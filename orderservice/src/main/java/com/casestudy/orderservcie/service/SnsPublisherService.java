package com.casestudy.orderservcie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SnsPublisherService {
	@Autowired
    private AmazonSNS amazonSNS;

    @Value("${sns.topic.arn}")
    private String snsTopicArn;

    public void publishOrderPlacedMessage(String emailId, String orderId) {
        try {
            OrderMessage orderMessage = new OrderMessage(emailId, orderId);
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(orderMessage);

            PublishRequest publishRequest = new PublishRequest(snsTopicArn, message);
            PublishResult publishResult= amazonSNS.publish(publishRequest);
            log.info(publishResult.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class OrderMessage {
        private String emailId;
        private String orderId;

        public OrderMessage(String emailId, String orderId) {
            this.emailId = emailId;
            this.orderId = orderId;
        }

        public String getEmailId() {
            return emailId;
        }

        public String getOrderId() {
            return orderId;
        }
    }
}
