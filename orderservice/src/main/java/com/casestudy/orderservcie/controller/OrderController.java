package com.casestudy.orderservcie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.orderservcie.dto.OrderRequest;
import com.casestudy.orderservcie.exceptions.CartEmptyException;
import com.casestudy.orderservcie.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orderservice")
@Slf4j
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public ResponseEntity<String> createOrder(@RequestBody OrderRequest request,HttpServletRequest servletRequest) throws CartEmptyException {
		System.out.println(request.toString());
		String message=orderService.createOrder(request.getUserId(),servletRequest);
		log.info(message);
		return new ResponseEntity<String>(message, HttpStatus.OK);	
	}
}
