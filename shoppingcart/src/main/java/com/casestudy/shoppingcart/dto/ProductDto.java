package com.casestudy.shoppingcart.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	
	private String productId;
	private String productName;
	private String description;
	private double quantity;
	private double pricePerUnit;

}
