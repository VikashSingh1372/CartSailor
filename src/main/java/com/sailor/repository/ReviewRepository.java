package com.sailor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sailor.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	
	@Query("Select r from Review r Where r.product.id=:productId")
	List<Review> getAllProductReview(Long productId);

}
