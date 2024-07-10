package com.casestudy.shoppingcart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.casestudy.shoppingcart.model.ShoppingCart;

public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String> {

}
