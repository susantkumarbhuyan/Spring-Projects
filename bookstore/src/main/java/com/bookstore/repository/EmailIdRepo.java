package com.bookstore.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bookstore.model.GetEmailId;

public interface EmailIdRepo extends MongoRepository<GetEmailId, Integer> {

	void save(Optional<GetEmailId> str);

}
