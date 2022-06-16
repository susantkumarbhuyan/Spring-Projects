package com.bookstore.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bookstore.model.Address;
import com.bookstore.model.Checkout;
import com.bookstore.payload.request.CheckoutRequest;


public interface CheckoutRepo extends MongoRepository<Checkout, Integer>{

	Checkout save(Address address);
	
	//Projection (Aggregation)
//	@Query(value="{}", fields="{totalCartPrice : 1, _id : 0}")
	@Aggregation(pipeline  ={
	"{'$project':{totalCartPrice:1}}"
})
	CheckoutRequest findTotalCartPrice();

}
