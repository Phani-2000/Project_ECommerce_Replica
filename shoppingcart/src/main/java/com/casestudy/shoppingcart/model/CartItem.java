package com.casestudy.shoppingcart.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("CartItems")
public class CartItem {
	
	@Id
	private String itemId;
	private String productName;
	private long quantity;
	private double price;
	

}
