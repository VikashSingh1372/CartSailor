package com.sailor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailor.entity.Order;
import com.sailor.entity.Product;
import com.sailor.exception.OrderException;
import com.sailor.exception.ProductException;
import com.sailor.request.CreateProductRequest;
import com.sailor.service.ProductService;

@RestController
@RequestMapping("api/admin/products")
public class AdminProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request){
		Product product = productService.createProduct(request);
	   return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<String>deleteOrderHandler(@PathVariable Long productId) throws ProductException{
		 productService.deleteProduct(productId);
		return new ResponseEntity<>("product deleted Sucessfully!!",HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProductHandler(){
		List<Product> product = productService.findAllProduct();
		return new ResponseEntity<List<Product>>(product,HttpStatus.ACCEPTED);
	}
	@PutMapping("/{productId}/update")
	public ResponseEntity<Product>updateProductHandler( @RequestBody Product request, @PathVariable Long productId
		) throws ProductException{
		Product product = productService.updateProduct(productId, request);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}
	
	@PostMapping("/multipleProduct")
	public ResponseEntity<String> createMultipleProduct(@RequestBody CreateProductRequest[] request){
		 for(CreateProductRequest productReq:request) {
			 Product product = productService.createMultipleProduct(request);

		 }
		
	   return new ResponseEntity<>("created Successfully !!!",HttpStatus.CREATED);
	}
}
