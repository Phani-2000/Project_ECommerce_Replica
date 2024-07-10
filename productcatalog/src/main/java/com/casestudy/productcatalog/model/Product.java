package com.casestudy.productcatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "productindex")
public class Product {
	
	@Id
	private String productId;
	private String productName;
	private String description;
	private double quantity;
	private double pricePerUnit;

}
