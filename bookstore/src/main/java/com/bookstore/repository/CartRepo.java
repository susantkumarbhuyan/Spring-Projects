package com.bookstore.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bookstore.model.Cart;

public interface CartRepo extends MongoRepository<Cart, Integer>{
	@Aggregation(pipeline = {
			"{$group: { _id: null , totalCartPrice : {$sum: $totalPrice}}}"
		})
		public Double sumPrices();



}
