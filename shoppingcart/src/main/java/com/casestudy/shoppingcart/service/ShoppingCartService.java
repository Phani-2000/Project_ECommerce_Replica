package com.casestudy.shoppingcart.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.casestudy.shoppingcart.dto.CartItemDto;
import com.casestudy.shoppingcart.dto.ProductDto;
import com.casestudy.shoppingcart.dto.ShoppingCartRequest;
import com.casestudy.shoppingcart.exception.ResourceNotFoundException;
import com.casestudy.shoppingcart.exception.ServiceUnavailableException;
import com.casestudy.shoppingcart.model.CartItem;
import com.casestudy.shoppingcart.model.ShoppingCart;
import com.casestudy.shoppingcart.repository.ShoppingCartRepository;
import com.casestudy.shoppingcart.security.JwtAuthFilter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingCartService {
	
	@Autowired
	private ShoppingCartRepository cartRepo;

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	private static final String PRODUCT_CATALOG_BASE_URL="http://localhost:8082";
	private static final String GET_PRODUCT_BY_NAME_URL = "/api/product/getByName/";
	private static final String UPDATE_PRODUCT_QUANTITY_URL = "/api/product/updateProductQuantity";

	public <T> T performRequest(String url, HttpMethod method, Object body, String bearerToken,
			Class<T> responseType) {
		WebClient.RequestBodySpec requestSpec = webClientBuilder.build().method(method).uri(PRODUCT_CATALOG_BASE_URL + url)
				.header(HttpHeaders.AUTHORIZATION, bearerToken);

		if (body != null) {
			requestSpec.bodyValue(body);
		}

		try {
			return requestSpec.retrieve().bodyToMono(responseType).block();
		} catch (WebClientResponseException ex) {
			if(ex.getRawStatusCode()==HttpStatus.NOT_FOUND.value()) {
				throw new ResourceNotFoundException("Product not found in ProductCatalog service", ex);
			}
			else {
				throw new ServiceUnavailableException("Error occurred while accessing ProductCatalog service", ex);
			}
			
		}
	}

	public ProductDto getProduct(String productName, String bearerToken) {
		String productUrl = GET_PRODUCT_BY_NAME_URL + productName;
		System.out.println("In getProd method");
		ProductDto product= performRequest(productUrl, HttpMethod.GET, null, bearerToken, ProductDto.class);
		return product;
	}

	public String updateProductQuantity(CartItemDto cartItemDto, String bearerToken) {
		String productUrl = UPDATE_PRODUCT_QUANTITY_URL;
		return performRequest(productUrl, HttpMethod.PUT, cartItemDto, bearerToken, String.class);
	}
	
	public ResponseEntity<String> processAddToCartRequest(ShoppingCartRequest request,
			HttpServletRequest servletRequest) {
		String bearerToken = servletRequest.getHeader("Authorization");
		CartItemDto cartItemDto = request.getCartItem();
		if(cartItemDto.getQuantity()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity should be atleast 1");
		}
		try {
			ProductDto product = getProduct(cartItemDto.getProductName(), bearerToken);
			System.out.println(product.toString());
			if(cartItemDto.getQuantity()>product.getQuantity()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity should be less than "+product.getQuantity()+" for this product");
			}
			log.info("Received product info from Product-Catalog: {}",product);
			double price = cartItemDto.getQuantity() * product.getPricePerUnit();

			CartItem cartItem = new CartItem(null, product.getProductName(), cartItemDto.getQuantity(), price);
			String cartId = addToCart(request.getUserId(), cartItem);
			log.info("{} successfully added to cart {}",cartItem.getProductName(),cartId);
			long newQuantity = (long) (product.getQuantity() - cartItem.getQuantity());
			cartItemDto.setQuantity(newQuantity);
			String message = updateProductQuantity(cartItemDto, bearerToken);
			log.info("Received update response from Product-Catalog {}", message);

			return ResponseEntity
					.ok("Item " + cartItemDto.getProductName() + " successfully added to user cart: " + cartId);
		}catch (ResourceNotFoundException ex) {
			log.info(cartItemDto.getProductName()+" doesn't exist in Product-Catalog");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (ServiceUnavailableException ex) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
		}
		
	}
	
	public String addToCart(String userId,CartItem cartItem) {
		ShoppingCart cart=cartRepo.findById(userId).orElse(new ShoppingCart(userId,new ArrayList<CartItem>()));
		List<CartItem> cartItems=cart.getCartItems();
		
		Optional<CartItem> existingCartItemOptional = cartItems.stream().filter(item->item.getProductName().equals(cartItem.getProductName())).findAny();
		if(existingCartItemOptional.isPresent()) {
			CartItem existingCartItem=existingCartItemOptional.get();
			existingCartItem.setQuantity(existingCartItem.getQuantity()+cartItem.getQuantity());
			existingCartItem.setPrice(existingCartItem.getPrice()+cartItem.getPrice());
		}
		else {
			cartItems.add(cartItem);
		}
		
		
		cart=cartRepo.save(cart);
		return cart.getUserId();
	}
	
	public ResponseEntity<String> processRemoveFromCartRequest(ShoppingCartRequest request,
			HttpServletRequest servletRequest) {
		String bearerToken = servletRequest.getHeader("Authorization");
		CartItemDto cartItemDto = request.getCartItem();

		try {
			ProductDto product = getProduct(cartItemDto.getProductName(), bearerToken);
			log.info("Received product info from Product-Catalog: {}",product);
			
			double price = cartItemDto.getQuantity() * product.getPricePerUnit();

			CartItem cartItem = new CartItem(null, product.getProductName(), cartItemDto.getQuantity(), price);
			
			String removeMessage = removeFromCart(request.getUserId(), cartItem);
			log.info(removeMessage);
			
			long newQuantity = (long) (product.getQuantity() + cartItemDto.getQuantity());
			cartItemDto.setQuantity(newQuantity);
			
			String message = updateProductQuantity(cartItemDto, bearerToken);
			log.info("Received update response from Product-Catalog {}", message);

			return ResponseEntity.ok(removeMessage);
		} catch (ResourceNotFoundException ex) {
			log.info(cartItemDto.getProductName()+" doesn't exist in Product-Catalog");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (ServiceUnavailableException ex) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
		}
	}
	
	public String removeFromCart(String userId,CartItem cartItem) {
		ShoppingCart cart=cartRepo.findById(userId).orElse(null);
		if(cart!=null) {
			List<CartItem> cartItems=cart.getCartItems();
			cartItems.forEach(item -> {
                if (item.getProductName().equals(cartItem.getProductName())) {
                    item.setQuantity(item.getQuantity() - cartItem.getQuantity());
                    item.setPrice(item.getPrice()-cartItem.getPrice());
                }
            });

            // Remove the product from the cart if the quantity becomes 0 or negative
            cartItems.removeIf(item -> item.getQuantity() <= 0);	
			cart=cartRepo.save(cart);
			return cartItem.getProductName()+" removed successfully from cart";
		}
		else {
			return "No cart found for this user";
		}
		
	}

	public ResponseEntity<ShoppingCart> getCart(String userId) {
		Optional<ShoppingCart> optionalCart = cartRepo.findById(userId);
		if(!optionalCart.isPresent()) {
			return new ResponseEntity<ShoppingCart>(HttpStatus.NOT_FOUND);
		}
		ShoppingCart cart = optionalCart.get();
		System.out.println("Sending cart");
		return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
	}

	public void clearCart(String userId) {
		Optional<ShoppingCart> optCart = cartRepo.findById(userId);
        if (optCart != null) {
        	ShoppingCart cart=optCart.get();
            cart.setCartItems(Collections.emptyList());
            cartRepo.save(cart);
        }
        System.out.println("Cleared cart for user");
		
	}
	

}
