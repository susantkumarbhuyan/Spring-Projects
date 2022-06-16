package com.bookstore.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.Order;
import com.bookstore.model.Product;
import com.bookstore.model.User;
import com.bookstore.service.OrderService;
import com.bookstore.service.ProductService;
import com.bookstore.service.UserService;



@RestController
@RequestMapping("/admin")
public class AdminController {

	private static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private	ProductService productService;	

	@Autowired
	private	UserService userService;

	@Autowired
	private	OrderService orderService;


	//-----------------------Product Section----------------------------------------------------------	

	//	1.	Add Products
	@PostMapping("/product/add")
	public ResponseEntity<?> addProducts(@RequestBody @Valid Product product) 
	{	
		String str = null;
		try {
			str=productService.addProduct(product);
		} catch (CustomeException e) {
			logger.error("Exception occurred in ProductService updateProduct", e);
		}
		if(str!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(product.getName() + " : Book "+  str);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body( product.getName()+" Book is Already Exist");
		//	throw new ProductNotFoundException( id +" Product is Not Found");	
	}
	//	4.	Edit (Update)Products
	@PutMapping("/product/update/{id}")
	public ResponseEntity<String> updateProductById(@PathVariable("id") int id, @RequestBody @Valid Product product) 
	{		
		String str = null;
		try {
			str=productService.updateProduct(id, product);
		} catch (CustomeException e) {
			logger.error("Exception occurred in ProductService updateProduct", e);
		}
		if(str!=null) {
			return ResponseEntity.ok().body( str);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id +" Product is Not Found");
		//		throw new ProductNotFoundException( id +" Product is Not Found");	
	}

	//	5.	Delete Products
	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<String> productDeleteById(@PathVariable("id") int id)
	{ String str = null;
	try {
		str=productService.deleteProduct(id);
	} catch (CustomeException e) {
		logger.error("Exception occurred in ProductService deleteProduct", e);
	}
	if(str!=null) {
		return ResponseEntity.ok().body( str);
	}
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id +" Product does not exist");
	//	throw new ProductNotFoundException( id +" Product is Not Found");	

	}

	//Delete All Products
	@DeleteMapping("/product/delete/all")
	public ResponseEntity<String> productAllDelete()
	{ ResponseEntity<String> tr=null;
	try {
		tr=ResponseEntity.status(HttpStatus.OK).body(productService.deleteAllProduct()) ;
	} catch (CustomeException e) {
		logger.error("Exception occurred in ProductService deleteAllProduct", e);
	}
	return tr;	
	}

	//-------------------------End Product Section--------------------------------------------------------

	//-------------------------User Section---------------------------------------------------------------
	//	6.	View  list  Users	
	@GetMapping("/dashboard/users")
	public ResponseEntity<?>  viewAllUsers() 
	{  
		ResponseEntity<?> tr=null;
		try {
			List<User> urList=  userService.viewUser();      //List<Order> oList= orderService.getAllOrders(uui); 
			if(urList.size()<1) {
				tr= ResponseEntity.status(HttpStatus.NOT_FOUND).body(" No Users");
			}
			else {
				tr= ResponseEntity.ok().body(urList);
			}
		} catch (CustomeException e) {
			logger.error("Exception occurred in OrderService getAllOrders", e);
		}
		return tr;
	}

	// View Single User 
	@GetMapping("/dashboard/users/{username}")
	public ResponseEntity<?> viewUserByID(@PathVariable("username") String username)
	{	
		User user=null;
		try {
			user= userService.viewUserByUsername(username);
		} catch (CustomeException e) {

			logger.error("Exception occurred in UserService viewUserById", e);
		}
		if(user!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body( username+ " User Not found");
	}

	//	7.	Delete Single Users 
	@DeleteMapping("/dashboard/user/delete/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username)
	{	String str=null;
	try {
		str=	userService.deleteUserByUsername(username);
	} catch (CustomeException e) {
		logger.error("Exception occurred in UserService viewUserById", e);
	}
	if(str==null){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body( username+ " User Not found");
	}
	return	ResponseEntity.status(HttpStatus.OK).body(str);	
	}

	//Delete All user
	@DeleteMapping("/dashboard/user/delete/all")
	public ResponseEntity<?> deleteAllUsers()
	{ ResponseEntity<?> tr= null;
	try {
		tr=ResponseEntity.status(HttpStatus.OK).body(userService.deleteAllUser() );
	} catch (CustomeException e) {
		logger.error("Exception occurred in UserService deleteAllUser", e);
	}
	return tr;	
	}
	//------------------------- End User -------------------------------------------------------------------

	//--------------------------- Start Order Section--------------------------------------------------------	
	//	8.	View All Orders
	@GetMapping("/dashbord/orders")
	public ResponseEntity<?> showAllOrder()
	{ ResponseEntity<?> tr=null;
	try {
		List<Order> oList= orderService.getAllOrder();       //List<Order> oList= orderService.getAllOrders(uui); 
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

	//	9.	Delete single 
	@DeleteMapping("/dashbord/orders/delete/{id}")
	public ResponseEntity<?> deleteOrdersById(@PathVariable("id") int id) 
	{ String str = null;
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
	//	throw new ProductNotFoundException( id+" Order id Doesn't Exist  Enter Valid Order Id");
	}

	// 10. Delete All Orders
	@DeleteMapping("/dashbord/orders/delete/all")
	public ResponseEntity<?> deleteAllOrders() 
	{ ResponseEntity<?> tr = null;
	try {
		tr =ResponseEntity.status(HttpStatus.OK).body(orderService.deleteAllOrder()) ;
	} catch (CustomeException e) {
		logger.error("Exception occurred in OrderService deleteAllOrder", e);
	}
	return tr ;
	}

	//	@GetMapping("/dashbord/orders/{date_from}/{date_to}")
	//	public ResponseEntity<?> getOrdersBetweenDates(@PathVariable("date_from") String date_from, @PathVariable("date_to") String date_to) 
	//	{ List<Order> str = null;
	//	try {
	//		str =orderService.getOrderByDate(date_from, date_to) ;
	//	} catch (CustomeException e) {
	//		logger.error("Exception occurred in OrderService deleteAllOrder", e);
	//	}
	//	if(str!=null)
	//	{
	//		return ResponseEntity.status(HttpStatus.OK).body(str); }
	//	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" No Products");
	//	}
	//--------------------------- End Order Section--------------------------------------------------------

	//-------------------------Category Section--------------------------------------------------------
	//	10.	Add Category 
	//	11.	Edit Category
	//	12.	Delete Category
	//-------------------------End Category Section--------------------------------------------------------




}
