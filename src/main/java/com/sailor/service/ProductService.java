package com.sailor.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sailor.entity.Product;
import com.sailor.exception.ProductException;
import com.sailor.request.CreateProductRequest;

public interface ProductService {

	public Product createProduct(CreateProductRequest request);

	public String deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product product) throws ProductException;

	public Product findProductById(long productId) throws ProductException;

	public List<Product> findProductByCategory(String category);

	 public Page<Product> getAllProduct(String category, List<String> color, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
	 
	 
	 public List<Product> findAllProduct();


	public Product createMultipleProduct(CreateProductRequest[] request);
}
