package com.bookstore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Document(collection = "database_sequence")
public class DatabaseSequence {

	@Id
	private String id;
	private long seq;
}
