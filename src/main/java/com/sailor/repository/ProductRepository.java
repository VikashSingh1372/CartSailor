package com.sailor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sailor.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p " +
		       "WHERE (:category IS null OR p.category.name = :category) " +
		       "AND (:minPrice IS null OR p.discountPrice >= :minPrice) " +
		       "AND (:maxPrice IS null OR p.discountPrice <= :maxPrice) " +
		       "AND (:minDiscount IS null OR p.discountPercent >= :minDiscount) " +
		       "ORDER BY " +
		       "CASE " +
		       "  WHEN :sort = 'price_low' THEN p.discountPrice " +
		       "  WHEN :sort = 'price_high' THEN p.discountPrice " +
		       "END"
		)
		public List<Product> filterProducts(
		       @Param("category") String category,
		       @Param("minPrice") Integer minPrice,
		       @Param("maxPrice") Integer maxPrice,
		       @Param("minDiscount") Integer minDiscount,
		       @Param("sort") String sorting
		);

}
