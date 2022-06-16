package com.bookstore.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.Product;
import com.bookstore.payload.request.LoginRequest;
import com.bookstore.payload.request.SignupRequest;
import com.bookstore.payload.response.MessageResponse;
import com.bookstore.service.ProductService;
import com.bookstore.service.SigninSignupService;

@RestController
@RequestMapping("/home")
public class HomeController {
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private	ProductService productService;

	@Autowired
	private SigninSignupService signinSignupService;

	//-------------------Home and Product Section -----------------------------------------------------	
	//	1.	ViewAllProducts
	@GetMapping("/product")
	public  ResponseEntity<?> showAllProducts()
	{
		ResponseEntity<?> tr=null;
		try {
			List<Product> plL=	productService.getAllProducts();
			if (plL.size()<1) {
				tr=ResponseEntity.status(HttpStatus.NOT_FOUND).body(" No Products");
			} else {
				tr= ResponseEntity.ok().body(plL);
			}
		} catch (CustomeException e) {
			logger.error("Exception occurred in ProductService getAllProducts", e);
		}
		return tr;
	}

	//	2.	View Single Product
	@GetMapping("/product/{id}")
	public ResponseEntity<?> showProductsById(@PathVariable("id") int id)
	{ 	
		Product str = null;
		try {
			str=productService.showProductById(id);
		} catch (CustomeException e) {
			logger.error("Exception occurred in CartService updateCart", e);
		}
		if(str!=null) {
			return ResponseEntity.ok().body(str);
		}
		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body( id+ " Product ID Does not exist");
		//	throw new NotFoundException( id+ " Product ID Does not exist");
	}


	//	3.	FilterProduct by Price(Aggregation)
	@GetMapping("/product/show/{minprice}/{maxprice}")
	public ResponseEntity<?> filterByPrices(@PathVariable(value = "minprice") int minprice, @PathVariable(value = "maxprice") int maxprice ) 
	{ ResponseEntity<?> tr =null;
	try {
		List<Product> prList= productService.filterByPrice(minprice, maxprice );
		if(prList.size()<1) {
			tr = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Products between " + minprice + " and "+ maxprice);
		}
		else {
			tr = ResponseEntity.ok(prList);
		}
	} catch (CustomeException e) {
		logger.error("Exception occurred in  ProductService filterByPrice", e);
	}
	return tr;
	}

	//	4.	showByCategory(Aggregation)
	@GetMapping("/product/showproduct/{name}")
	public ResponseEntity <?> showProductsByName(@PathVariable(value = "name") String name)
	{ ResponseEntity<?> tr =null;
	try {
		List<Product> list=productService.showProductByCategoty(name);
		if(list.size()<1)
		{
			tr = ResponseEntity.status(HttpStatus.NOT_FOUND).body( name +" Category  does not exist");
		}
		else {
			tr = ResponseEntity.ok(list);
		}
	} catch (CustomeException e) {
		logger.error("Exception occurred in  ProductService showProductByCategoty", e);
	}
	return tr;
	}
	//5. search by name 
	@GetMapping("/product/search/{name}")
	public ResponseEntity<?>  searchByName(@PathVariable(value = "name") String name) 
	{ ResponseEntity<?> tr =null;
	try {
		List<Product> ptList = productService.searchByName(name);
		if(ptList.size()<1) {
			tr = ResponseEntity.status(HttpStatus.NOT_FOUND).body( name + " Product Not Found");
		}
		else {
			tr = ResponseEntity.ok().body(ptList);
		}
	} catch (CustomeException e) {
		logger.error("Exception occurred in  ProductService searchByName", e);
	}
	return tr;
	}

	//6.	Sort By price Ascending order
	@GetMapping("/product/sort/{sortType}") //(1: for Asc Order  and -1 : for Desc Order)
	public ResponseEntity<?>  sortByPrice(@PathVariable("sortType") int sortType) 
	{ ResponseEntity<?> tr=null;
	try {
		if(sortType==1 | sortType==-1) {
			tr= ResponseEntity.ok(productService.sortByPrice(sortType));
		}
		else {
			tr= ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sortType Must be 1 or -1");
		}

	} catch (CustomeException e) {
		logger.error("Exception occurred in  ProductService sortByPrice", e);
	}
	return tr ;
	}	

	//------------------- End Home and Product Section ---------------------------------------------------------

	//--------------------------------SignIn and SignUp Section--------------------------------------------------
	//	5. SignIn()

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) 
	{ ResponseEntity<?> tr=null;
	try {
		tr = signinSignupService.signIn(loginRequest);
	} catch (CustomeException e) {
		logger.error("Exception occurred in  signinSignupService SignUp", e);
	}
	if (tr!=null) {
		return tr;
	}
	return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new MessageResponse("User Not Found!"));
	}

	//	6.	SignUp()
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
	{
		ResponseEntity<?> tr=null;
		try {
			tr =signinSignupService.signUp(signUpRequest);
		} catch (CustomeException e) {
			logger.error("Exception occurred in  signinSignupService SignUp", e);
		}
		return tr;
	}

	//	7.	ForgetPassword()
	//---------------------------- End Login and SignUp Section--------------------------------------------------


}
