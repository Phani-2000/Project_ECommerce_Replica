package com.casestudy.shoppingcart.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("ShoppingCart")
public class ShoppingCart {
	@Id
	private String userId;
	private List<CartItem> cartItems;
	
	public ShoppingCart(String userId){
		this.userId=userId;
	}
	
}
