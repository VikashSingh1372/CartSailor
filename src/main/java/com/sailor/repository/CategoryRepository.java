package com.sailor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sailor.entity.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
	
	
	public Category findByName(String name);
	
	
	@Query("Select c from Category c Where c.name=:name AND c.parentCategory.name=:parentCategory")
	public Category findByNameAndParent(@Param("name") String name, @Param("parentCategory") String parentCategory);

}
