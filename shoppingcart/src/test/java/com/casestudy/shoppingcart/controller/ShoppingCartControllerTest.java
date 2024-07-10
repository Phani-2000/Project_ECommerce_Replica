package com.casestudy.shoppingcart.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.casestudy.shoppingcart.controllers.ShoppingCartController;
import com.casestudy.shoppingcart.dto.CartItemDto;
import com.casestudy.shoppingcart.dto.ShoppingCartRequest;
import com.casestudy.shoppingcart.service.ShoppingCartService;

public class ShoppingCartControllerTest {
	
	@Mock
	private ShoppingCartService cartService;
	
	@InjectMocks
    private ShoppingCartController cartController;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void testAddToCart() {
		// Mocking the service method
        when(cartService.processAddToCartRequest(any(), any())).thenReturn(
                ResponseEntity.ok("Item successfully added to cart"));

        // Creating a request
        ShoppingCartRequest request = new ShoppingCartRequest("user1", new CartItemDto("Product", 2));

        // Calling the controller method
        ResponseEntity<String> response = cartController.addToCart(request, null);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Item successfully added to cart", response.getBody());
	}
	
	@Test
	public void testRemoveFromCart() {
		when(cartService.processRemoveFromCartRequest(any(), any())).thenReturn(
                ResponseEntity.ok("removed successfully from cart"));

        // Creating a request
        ShoppingCartRequest request = new ShoppingCartRequest("user1", new CartItemDto("Product", 2));

        // Calling the controller method
        ResponseEntity<String> response = cartController.removeFromCart(request, null);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("removed successfully from cart", response.getBody());
	}

}
