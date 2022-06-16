package com.bookstore.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bookstore.model.Product;



public interface ProductRepo extends MongoRepository<Product, Integer> {
	@Aggregation(pipeline  ={
			"{'$match':{'name':{'$regex': '?0','$options':'i'}}}",
			"{'$sort' : {'price': 1}}",
			"{'$project':{id:1, name:1,price:1, pages:1, isbnNo:1, language:1,category:1}}"
	})
	List<Product> findByProductName(String name);


	@Aggregation(pipeline  ={
			"{'$sort' : {'price': ?0}}",
			"{'$project':{id:1, name:1, price:1, pages:1,isbnNo:1, language:1,category:1}}"
	})
	List<Product> sortByPrice(int sortType);

	@Aggregation(pipeline  ={
			"{'$match':{'category':?0}}",
			"{'$sort' : {'price': 1}}",
			"{'$project':{id:1, name:1, price:1, pages:1,isbnNo:1, language:1,category:1}}"
	})
	List<Product> findProductByCategory(String name);

	@Aggregation(pipeline  ={
			"{'$match':{ 'price': { $gt: ?0, $lt: ?1 } }}",
			"{'$sort' : {'price': 1}}",
			"{'$project':{id:1, name:1, price:1, pages:1,isbnNo:1, language:1,category:1}}"
	})
	List<Product> filterByPrice(int minprice, int maxprice);
	
	
	
	
	
	//	@Aggregation(pipeline  ={
	//			"{'$match':{ 'isbnNo': ?0}}"
	//	})
	//	Product findByIsbnNo( long isbnNo);


	@Aggregation(pipeline  ={
			"{'$match':{'isbnNo': '?0'}}"
	})
	Product findProductByIsbn(String isbnNo);





	//	https://stackabuse.com/spring-data-mongodb-guide-to-the-aggregation-annotation/
}
