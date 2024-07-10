package com.casestudy.productcatalog.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.casestudy.productcatalog.model.Product;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
	
	Optional<Product> findByProductName(String productName);

}
