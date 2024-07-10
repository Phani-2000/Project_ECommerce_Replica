package com.casestudy.productcatalog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.productcatalog.model.Product;
import com.casestudy.productcatalog.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	public Iterable<Product> findAllProducts() {
		Iterable<Product> products = productRepo.findAll();
		return products;
	}

	public String addProduct(Product product) {
		Product saved = productRepo.save(product);
		return saved.getProductId();
	}

	public Product findProductById(String id) {
		return productRepo.findById(id).orElse(null);
	}
	
	public Product findProductByName(String productName) {
		return productRepo.findByProductName(productName).orElse(null);
	}
	

	public void deleteProduct(String id) {
		productRepo.deleteById(id);
	}

	public String updateProductQuantity(String productName, long quantity) {
		Optional<Product> optionalProduct = productRepo.findByProductName(productName);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setQuantity(quantity);
			productRepo.save(product);
			return "Updated product :" + product.getProductName();
		} else {
			return null;
		}
	}

}
