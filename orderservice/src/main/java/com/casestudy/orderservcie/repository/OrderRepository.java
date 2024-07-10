package com.casestudy.orderservcie.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.casestudy.orderservcie.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

}
