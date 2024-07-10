package com.casestudy.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateQuantityDto {
	
	private String productName;
	private Long quantity;

}
