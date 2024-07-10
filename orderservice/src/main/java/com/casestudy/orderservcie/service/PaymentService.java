package com.casestudy.orderservcie.service;

import org.springframework.stereotype.Service;

import com.casestudy.orderservcie.dto.PaymentResponse;


@Service
public class PaymentService {
	
	private double availableAccountBalance=5000;
	
	public PaymentResponse processPayment(double amount) {
		System.out.println("In payment service");
		PaymentResponse paymentResponse=new PaymentResponse();
		if(amount<availableAccountBalance) {
			paymentResponse.setSuccess(true);
			paymentResponse.setMessage("Payment successful. Thank you for your purchase!");
		}
		else {
			paymentResponse.setSuccess(false);
			paymentResponse.setMessage("Payment failed. Please try again.");
		}
		return paymentResponse;
	}

}
