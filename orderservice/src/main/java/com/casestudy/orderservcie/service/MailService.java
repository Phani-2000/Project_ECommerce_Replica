package com.casestudy.orderservcie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	public void sendMail(String orderId, String userId) {
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		String message="Your order has been placed successfully!. OrderId is "+orderId;
		mailMessage.setFrom(fromMail);
		mailMessage.setSubject("Order Confirmation");
		mailMessage.setText(message);
		mailMessage.setTo(userId);
		javaMailSender.send(mailMessage);
	}

}
