package com.sailor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sailor.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	@Query("Select r from Rating r Where r.product.id=:productId")
	List<Rating> getAllProductRating(Long productId);

}
