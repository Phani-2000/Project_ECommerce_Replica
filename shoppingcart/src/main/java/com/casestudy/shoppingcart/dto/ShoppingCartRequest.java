package com.casestudy.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartRequest {
	
	private String userId;
	private CartItemDto cartItem;

}
