package com.bookstore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document (collection = "emailid")
public class GetEmailId {
	@Id
	private int id;
	private String emailId;

}
