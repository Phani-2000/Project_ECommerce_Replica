package com.casestudy.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductDto {
	
	private String productName;
	private Long quantity;
}
