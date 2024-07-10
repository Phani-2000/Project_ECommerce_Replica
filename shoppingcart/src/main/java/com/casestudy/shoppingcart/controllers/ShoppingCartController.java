package com.casestudy.shoppingcart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.shoppingcart.dto.ShoppingCartRequest;
import com.casestudy.shoppingcart.model.ShoppingCart;
import com.casestudy.shoppingcart.service.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService cartService;
	
	
	@PostMapping("/addToCart")
	public ResponseEntity<String> addToCart(@RequestBody ShoppingCartRequest request,
			HttpServletRequest servletRequest) {
		System.out.println(request.toString());
		return cartService.processAddToCartRequest(request, servletRequest);
	}

	@DeleteMapping("/removeFromCart")
	public ResponseEntity<String> removeFromCart(@RequestBody ShoppingCartRequest request,
			HttpServletRequest servletRequest) {
		return cartService.processRemoveFromCartRequest(request, servletRequest);
	}
	
	@GetMapping("/getCart/{userid}")
	public ResponseEntity<ShoppingCart> getCart(@PathVariable("userid") String userId){
		System.out.println(userId);
		return cartService.getCart(userId);
	}
	
	@DeleteMapping("/clearCart/{userid}")
    public ResponseEntity<String> clearCart(@PathVariable("userid") String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
	

}
