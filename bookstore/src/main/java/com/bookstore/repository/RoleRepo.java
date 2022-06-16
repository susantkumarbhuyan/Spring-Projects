package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookstore.model.ERole;
import com.bookstore.model.Role;

public interface RoleRepo extends MongoRepository<Role, String> {
	  Optional<Role> findByName(ERole name);

}
