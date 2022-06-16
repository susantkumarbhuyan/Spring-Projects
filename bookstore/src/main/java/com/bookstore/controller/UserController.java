package com.bookstore.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.Order;
import com.bookstore.model.User;
import com.bookstore.service.OrderService;
import com.bookstore.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private	UserService userService;

	@Autowired
	private	OrderService orderService;

	//	1.	ShowAllOrders
	@GetMapping("/dashbord/orders")           //	@GetMapping("/dashbord/orders/{uui}")
	public ResponseEntity<?> showAllOrder()     //public ResponseEntity<?> showAllOrder(@PathVariable("uui") String uui) 
	{ ResponseEntity<?> tr=null;
	try {
		List<Order> oList= orderService.getAllOrders();       //List<Order> oList= orderService.getAllOrders(uui); 
		if(oList.size()<1) {
			tr= ResponseEntity.status(HttpStatus.NOT_FOUND).body(" No Orders");
		}
		else {
			tr= ResponseEntity.ok().body(oList);
		}
	} catch (CustomeException e) {
		logger.error("Exception occurred in OrderService getAllOrders", e);
	}
	return tr;
	}

	//	2.	Delete single 
	@DeleteMapping("/dashbord/orders/delete/{id}")
	public ResponseEntity<?> deleteOrdersById(@PathVariable("id") int id)
	{String str = null;
	try {
		str=	orderService.deleteOrderById(id);
	} catch (CustomeException e) {
		logger.error("Exception occurred in OrderService deleteOrderById", e);
	}
	if(str!=null)
	{
		return	ResponseEntity.status(HttpStatus.OK).body(str); 
	}
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id+" Order id Doesn't Exist  Enter Valid Order Id");
	//throw new ProductNotFoundException( id+" Order id Doesn't Exist  Enter Valid Order Id");
	}
	
	// 2.1 Delete All Orders
	@DeleteMapping("/dashbord/orders/delete/all")
	public ResponseEntity<?> deleteAllOrders() 
	{ ResponseEntity<?> tr=null; 
	try {
		tr= ResponseEntity.ok(orderService.deleteAllOrderByUui()) ;
	} catch (CustomeException e) {
		logger.error("Exception occurred in OrderService deleteAllOrderByUui", e);
	}
	return tr;
	}

	//	3.	ShowUserDetails
	@GetMapping("/dashboard/details")

	public ResponseEntity<?> viewUserDetails() throws Exception
	{ ResponseEntity<?> tr=null;
	try {
		User user= userService.userviewUserDetails();
		if(user==null) {
			tr = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		else {
			tr = ResponseEntity.ok().body(user);
		}
	} catch (CustomeException e) {
		logger.error("Exception occurred in OrderService userviewUserDetails", e);
	}
	return tr;
	}


	//	4.	Edit UserDetails
	@PutMapping("/dashboard/details/update")
	public ResponseEntity<?> updateUserDetails(@RequestBody User user)
	{ ResponseEntity<?> tr = null;
	try {
		tr =ResponseEntity.ok(userService.updateUserDetail(user));
	} catch (CustomeException e) {
		logger.error("Exception occurred in OrderService updateUserDetail", e);
	}
	return tr;
	}


}
