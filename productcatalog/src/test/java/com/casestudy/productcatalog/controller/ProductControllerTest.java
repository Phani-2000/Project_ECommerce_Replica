package com.casestudy.productcatalog.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.casestudy.productcatalog.dto.UpdateQuantityDto;
import com.casestudy.productcatalog.model.Product;
import com.casestudy.productcatalog.security.JwtValidator;
import com.casestudy.productcatalog.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JwtValidator jwtValidator;
	@MockBean
	private ProductService productService;
	@Autowired
	private ObjectMapper mapper;
	private Product product;
	private UpdateQuantityDto updateQuantityDto;
	
	@BeforeEach
	public void init() {
		product=new Product("1","Product1","Description1",100,100);
		updateQuantityDto=new UpdateQuantityDto("Prodt1",1l);
	}
	
	@Test
	public void testAddProduct() throws JsonProcessingException, Exception {
		when(productService.addProduct(product)).thenReturn("1");
		
		ResultActions response=mockMvc.perform(post("/api/product/addProduct")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer token")
				.content(mapper.writeValueAsString(product)));
		
		response.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void testUpdateProduct() throws JsonProcessingException, Exception {
		when(productService.updateProductQuantity(updateQuantityDto.getProductName(),updateQuantityDto.getQuantity())).thenReturn("Updated");
		
		ResultActions response=mockMvc.perform(put("/api/product/updateProductQuantity")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer token")
				.content(mapper.writeValueAsString(updateQuantityDto)));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("Updated"));
	}
	
	@Test
	public void testDeleteProduct() throws JsonProcessingException, Exception {
		doNothing().when(productService).deleteProduct(anyString());
		ResultActions response=mockMvc.perform(delete("/api/product//deleteProduct/Prodt1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer token"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("Product deleted with id:Prodt1"));
	}
	
	@Test
	public void testFindByName() throws JsonProcessingException, Exception {
		when(productService.findProductByName("Product1")).thenReturn(product);
		ResultActions response=mockMvc.perform(get("/api/product/getByName/Product1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer token"));
		
		response.andExpect(status().isFound())
		.andExpect(jsonPath("$.productName", is("Product1")));
	}
	
	@Test
	public void testFindById() throws JsonProcessingException, Exception {
		when(productService.findProductById("1")).thenReturn(product);
		ResultActions response=mockMvc.perform(get("/api/product/getById/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer token"));
		
		response.andExpect(status().isFound())
		.andExpect(jsonPath("$.productName", is("Product1")));
	}
	
	@Test
	public void testFindAll() throws JsonProcessingException, Exception {
		List<Product> prodList=new ArrayList<Product>();
		prodList.add(product);
		when(productService.findAllProducts()).thenReturn(prodList);
		ResultActions response=mockMvc.perform(get("/api/product/getAllProducts")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer token"));
		
		response.andExpect(MockMvcResultMatchers.status().isFound());
	}
}
