package com.bookstore.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "cart")
@Data
public class Cart {
	@Id
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	private int id;
	//@DBRef()
	public Product product;
	private int quantity;
	private double totalPrice;

}
