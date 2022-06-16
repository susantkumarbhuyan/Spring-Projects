package com.bookstore.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bookstore.model.Checkout;
import com.bookstore.model.Order;

public interface OrderRepo extends MongoRepository<Order, Integer> {

	Order save(Checkout checkout);

	List<Order> findAllByEmailId(String emailId);

	void deleteAllByEmailId(String emailId);

//	@Aggregation(pipeline  ={
//			"{'$match':{ 'price': { $gt: ?0, $lt: ?1 } }}",
//			"{'$sort' : {'orderDate': 1}}",
//	})
//	List<Order> getOrderByDate(String date_from, String date_to);

}
