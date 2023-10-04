package com.sailor.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sailor.entity.Category;
import com.sailor.entity.Product;
import com.sailor.exception.ProductException;
import com.sailor.repository.CategoryRepository;
import com.sailor.repository.ProductRepository;
import com.sailor.request.CreateProductRequest;
import com.sailor.service.ProductService;
import com.sailor.service.UserService;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private UserService userService;

	@Override
	public Product createProduct(CreateProductRequest request) {

		Category firstLevelCategory = categoryRepo.findByName(request.getFirstLevelCategory());

		if (firstLevelCategory == null) {
			Category firstLevel = new Category();
			firstLevel.setName(request.getFirstLevelCategory());
			firstLevel.setLevel(1);
			firstLevelCategory = categoryRepo.save(firstLevel);
		}
		Category secondLevelCategory = categoryRepo.findByNameAndParent(request.getSecondLevelCategory(),
				firstLevelCategory.getName());

		if (secondLevelCategory == null) {
			Category secondLevel = new Category();
			secondLevel.setName(request.getSecondLevelCategory());
			secondLevel.setLevel(2);
			secondLevel.setParentCategory(firstLevelCategory);
			secondLevelCategory = categoryRepo.save(secondLevel);
		}

		Category thirdLevelCategory = categoryRepo.findByNameAndParent(request.getThirdLevelCategory(),
				secondLevelCategory.getName());

		if (thirdLevelCategory == null) {
			Category thirdLevel = new Category();
			thirdLevel.setName(request.getThirdLevelCategory());
			thirdLevel.setLevel(3);
			thirdLevel.setParentCategory(secondLevelCategory);
			thirdLevelCategory = categoryRepo.save(thirdLevel);
		}

		Product product = new Product();
		product.setBrand(request.getBrand());
		product.setCategory(thirdLevelCategory);
		product.setColor(request.getColor());
		product.setCreatedAt(LocalDateTime.now());
		product.setDescription(request.getDescription());
		product.setDiscountPercent(request.getDiscountPercent());
		product.setDiscountPrice(request.getDiscountPrice());
		product.setPrice(request.getPrice());
		product.setQuantity(request.getQuantity());
		product.setSizes(request.getSizes());
		product.setImageUrl(request.getImageUrl());
		product.setTitle(request.getTitle());

		Product savedProduct = productRepo.save(product);

		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		// TODO Auto-generated method stub
		Product product = productRepo.findById(productId).get();
		if (product == null) {
			throw new ProductException("product is not avialable with id :" + productId);
		}
		product.getSizes().clear();
		productRepo.deleteById(productId);

		return "Product deleted Successfully!!!";
	}

	@Override
	public Product updateProduct(Long productId, Product request) throws ProductException {
		Product product = productRepo.findById(productId).get();
		if (product == null) {
			throw new ProductException("product is not avialable with id :" + productId);
		}

		if (request.getQuantity() != 0) {
			product.setQuantity(request.getQuantity());
		}

		return productRepo.save(product);
	}

	@Override
	public Product findProductById(long productId) throws ProductException {

		Optional<Product> product = productRepo.findById(productId);
		if (product.isPresent()) {
			return product.get();

		}
		throw new ProductException("product not found with id :" + productId);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> color, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<Product> products = productRepo.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

		if (!color.isEmpty()) {
			products = products.stream().filter(p -> color.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}

		if (stock != null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());

			} else if (stock.equals("Out of Stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());

			}
		}

		// pagination
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
		List<Product> pageContent = products.subList(endIndex, endIndex);

		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());

		return filteredProducts;
	}

	@Override
	public List<Product> findAllProduct() {
		List<Product> products = productRepo.findAll();
		return products;
	}

	@Override
	public Product createMultipleProduct(CreateProductRequest[] request) {
		// TODO Auto-generated method stub
		return null;
	}

}
