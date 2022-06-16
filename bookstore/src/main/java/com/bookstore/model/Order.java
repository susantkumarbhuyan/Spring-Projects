package com.bookstore.model;


import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;



@Document(collection = "order")
@Data
public class Order {
//	@Autowired
//	UserService userService;
	@Id
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	private int id;
	private String emailId;  // for  particular user Order List by Email
	private String name;
//	@DBRef
	private List<Checkout> products;
	private String orderDate;
	private Double amount;
	private String paymentWay;

}
