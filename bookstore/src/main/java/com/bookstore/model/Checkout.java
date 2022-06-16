package com.bookstore.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "checkout")
@Data
@NoArgsConstructor
public class Checkout {
	@Id
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	private String id;
	private List<Cart> cart;
	private Address address;
	private double totalCartPrice;

}
