package com.casestudy.orderservcie.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.casestudy.orderservcie.dto.PaymentResponse;
import com.casestudy.orderservcie.exceptions.CartEmptyException;
import com.casestudy.orderservcie.model.CartItem;
import com.casestudy.orderservcie.model.Order;
import com.casestudy.orderservcie.model.ShoppingCart;
import com.casestudy.orderservcie.repository.OrderRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private PaymentService paymentService;
	
	@Autowired 
	private MailService mailService;
	 
	//@Autowired
	//private SnsPublisherService snsPublisherService;
	
	@Autowired
	private WebClient webClient;
	
	private static final String CART_URL="http://localhost:8081/api/shoppingcart" ;

	public String createOrder(String userId, HttpServletRequest servletRequest) throws CartEmptyException {
		// Get the user's cart

		String bearerToken = servletRequest.getHeader("Authorization");
		ShoppingCart cart = webClient.get().uri(CART_URL+"/getCart/{userid}", userId)
				.header(HttpHeaders.AUTHORIZATION, bearerToken).retrieve().bodyToMono(ShoppingCart.class).block();

		// Check if cart is null or empty
		if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
			throw new CartEmptyException("Cart is empty for user " + userId);
		}

		double totalPrice = cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum();
		PaymentResponse paymentResponse = paymentService.processPayment(totalPrice);

		if (!paymentResponse.isSuccess()) {
			return "Couldn't place the Order as payment was failed";
		}

		return processOrderCreation(userId, cart, totalPrice, bearerToken);

	}

	private String processOrderCreation(String userId, ShoppingCart cart, double totalPrice, String bearerToken) {
		Order order = new Order();
		order.setUserId(userId);
		order.setTotalPrice(totalPrice);
		order.setOrderItems(cart.getCartItems());
		order.setOrderDate(new Date());

		order = orderRepo.save(order);
		webClient.delete().uri(CART_URL+"/clearCart/{userid}", userId).header(HttpHeaders.AUTHORIZATION, bearerToken).retrieve()
				.bodyToMono(String.class).block();
		triggerEmail(order.getOrderId(), userId);
		log.info("Confirmation mail sent to user {}", userId);
		return "Order created with id: " + order.getOrderId();
	}

	private void triggerEmail(String orderId, String userId) {
		System.out.println("Triggering email");
		mailService.sendMail(orderId, userId);
		//snsPublisherService.publishOrderPlacedMessage(userId, orderId);

	}

}
