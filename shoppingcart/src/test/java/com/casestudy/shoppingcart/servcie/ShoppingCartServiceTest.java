package com.casestudy.shoppingcart.servcie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import com.casestudy.shoppingcart.dto.CartItemDto;
import com.casestudy.shoppingcart.dto.ProductDto;
import com.casestudy.shoppingcart.dto.ShoppingCartRequest;
import com.casestudy.shoppingcart.model.CartItem;
import com.casestudy.shoppingcart.model.ShoppingCart;
import com.casestudy.shoppingcart.repository.ShoppingCartRepository;
import com.casestudy.shoppingcart.service.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

public class ShoppingCartServiceTest {
	
	@Mock
    private ShoppingCartRepository cartRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private ShoppingCartService cartService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToCart() {
        // Mocking dependencies
        when(cartRepository.findById(anyString())).thenReturn(Optional.of(new ShoppingCart("user1", new ArrayList<>())));
        
        when(cartRepository.save(any())).thenReturn(new ShoppingCart("user1", List.of(new CartItem(null,"Product", 2,100))));

        // Testing the method
        String response = cartService.addToCart("user1", new CartItem(null,"Product", 2,100));
        

        // Verifying the response
        assertEquals("user1", response);
    }
   

}
