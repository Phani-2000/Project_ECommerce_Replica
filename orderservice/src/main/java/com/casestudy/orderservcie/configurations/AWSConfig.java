package com.casestudy.orderservcie.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class AWSConfig {
	/*
	 * @Value("${aws.accessKeyId}") private String accessKeyId;
	 * 
	 * @Value("${aws.secretKey}") private String secretKey;
	 * 
	 * 
	 * @Bean public AmazonSNS amazonSNS() { BasicAWSCredentials awsCreds = new
	 * BasicAWSCredentials(accessKeyId, secretKey); return
	 * AmazonSNSClientBuilder.standard() .withRegion(Regions.AP_SOUTH_1)
	 * .withCredentials(new AWSStaticCredentialsProvider(awsCreds)) .build(); }
	 */

}
