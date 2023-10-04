package com.sailor.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemException extends Exception {
	
	
	public CartItemException(String message) {
		super(message);
	}

}
