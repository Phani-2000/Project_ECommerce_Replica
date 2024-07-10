package com.casestudy.productcatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.productcatalog.dto.UpdateQuantityDto;
import com.casestudy.productcatalog.model.Product;
import com.casestudy.productcatalog.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/getAllProducts")
	public ResponseEntity<Iterable<Product>> findAllProducts() {
		Iterable<Product> products = productService.findAllProducts();
		System.out.println(products);
		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable("id") String id){
		Product product=productService.findProductById(id);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@GetMapping("/getByName/{name}")
	public ResponseEntity<Product> findProductByName(@PathVariable("name") String name){
		Product product=productService.findProductByName(name);
		if(product==null) {
			log.info("No product exists with name {}",name);
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		log.info(product.toString());
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") String id){
		productService.deleteProduct(id);
		return new ResponseEntity<String>("Product deleted with id:"+id, HttpStatus.OK);
	}
	
	@PutMapping("/updateProductQuantity")
	public ResponseEntity<String> updateProductQuantity(@RequestBody UpdateQuantityDto updateQuantityDto){
		log.info("Received update request is {}",updateQuantityDto.toString());
		String message=productService.updateProductQuantity(updateQuantityDto.getProductName(), updateQuantityDto.getQuantity());
		if(message==null) {
			log.info("Couldn't find the Product with id:"+updateQuantityDto.getProductName());
			return new ResponseEntity<String>("Couldn't find the Product with id:"+updateQuantityDto.getProductName(), HttpStatus.NOT_FOUND);
		}
		log.info(message);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody Product product){
		String id=productService.addProduct(product);
		return new ResponseEntity<String>("Product added with id :"+id, HttpStatus.CREATED);
	}


}
