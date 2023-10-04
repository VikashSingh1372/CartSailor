package com.sailor.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentInformation {

	@Column(name = "cardholder_name")
	private String cardholderName;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "expiration_date")
	private String expirationDate;
	
	private String cvv;

}
