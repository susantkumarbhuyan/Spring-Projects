package com.bookstore.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;







@JsonInclude(JsonInclude.Include.NON_NULL)     // for remove json null value fields
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {
	@Id
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	private int id; 
	@NotBlank(message = "Name may not be blank")
	private String name;
	@NotNull(message = "price may not be null")
	private int price;
	private int pages;
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid Isbn No entered")
	private String isbnNo;
	private String author;
	private String language;
	@NotBlank(message = "Category may not be blank")
	private String category;
	private String description;

}
