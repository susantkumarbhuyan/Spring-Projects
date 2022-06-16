package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.bookstore.model.User;

public interface UserRepo extends MongoRepository<User, String> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

	Optional<User> findByEmailId(String emailId);
	
//	@Aggregation(pipeline  ={
//			"{'$match':{ '$roles': { 'name': 'ROLE_USER'} }}"
//	})
//	List<User> findAllUser();

}
