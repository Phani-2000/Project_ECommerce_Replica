package com.casestudy.orderservcie.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Order")
public class Order {
	@Id
	private String orderId;
	private String userId;
	private Date orderDate;
	private List<CartItem> orderItems;
	private double totalPrice;

}
