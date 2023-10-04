package com.sailor.request;

import java.util.HashSet;
import java.util.Set;
import com.sailor.entity.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CreateProductRequest {
	private String title;
	private String description;

	private int price;

	private int discountPrice;

	private int discountPercent;
	private int quantity;
	private String brand;
	private String color;

	private Set<Size> sizes = new HashSet<>();
	private String imageUrl;

	private String firstLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;

}
