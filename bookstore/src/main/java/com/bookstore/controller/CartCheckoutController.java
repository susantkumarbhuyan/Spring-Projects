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
import org.springframework.web.bind.annotation.RestController;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.Address;
import com.bookstore.model.Cart;
import com.bookstore.model.Checkout;
import com.bookstore.service.CartService;
import com.bookstore.service.CheckoutService;

@RestController

public class CartCheckoutController {
	private static Logger logger = LoggerFactory.getLogger(CartCheckoutController.class);

	@Autowired
	private CartService cartService;
	@Autowired
	private CheckoutService checkoutService;

	// 1. AddToCart
	@PostMapping("/cart/add/{id}/{quantity}")
	public ResponseEntity<?> addToCart(@PathVariable("id") int id, @PathVariable("quantity") int quantity)
	{ String str = null;
	try {
		str=cartService.addCart(id, quantity);
	} catch (CustomeException e) {
		logger.error("Exception occurred in  CartService addCart", e);
	}
	if(str!=null) {
		return ResponseEntity.ok().body(str);
	}
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id +" Product Not Found");
	//throw new ProductNotFoundException( id +" Product Not Found");	
	}

	// 2. ViewCart
	@GetMapping("/cart")
	public ResponseEntity<?> showCartItems(){
		ResponseEntity<?> tr=null;
		try {
			List<Cart>	cList=cartService.showCart();
			if (cList.size()<1) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Products in Cart");
//				throw new ProductNotFoundException("No Products in Cart");
			} else {
				tr= ResponseEntity.status(HttpStatus.OK).body(cList);
			}

		} catch (CustomeException e) {
			logger.error("Exception occurred in CartService showCart", e);
		}
		return tr;
	}

	// 3. RemoveCartItem
	@DeleteMapping("/cart/delete/{id}")
	public ResponseEntity<?> deleteCartItem(@PathVariable("id") int id) {
		return	ResponseEntity.ok(cartService.deleteCart(id));
	}

	// Remove All Items From Cart
	@DeleteMapping("/cart/delete/all")
	public ResponseEntity<?> deleteAllCartItem() {
		ResponseEntity<?>  tr=null;
		try {
			tr=ResponseEntity.ok( cartService.deleteAllCart());
		} catch (CustomeException e) {
			logger.error("Exception occurred in CartService deleteAllCart", e);
		}
		return tr;
	}

	//	5.	Edit Cart (Update Quantity BY Id)
	@PutMapping("/cart/update/{id}/{quantity}")
	public ResponseEntity<?> updateCartItem(@PathVariable("id") int id,@PathVariable("quantity") int quantity) {
		String str = null;
		try {
			str=cartService.updateCart(id, quantity);
		} catch (CustomeException e) {
			logger.error("Exception occurred in CartService updateCart", e);
		}
		if(str!=null) {
			return ResponseEntity.ok().body(str);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id +" Product Not Found");	
	//	throw new ProductNotFoundException( id +" Product Not Found");	
	}

	// 4. Checkout
	@PostMapping("/checkout")
	public ResponseEntity<?> checkOut(@RequestBody @Valid Address address) {
		Checkout str = null;
		try {
			str=checkoutService.addCheckout(address);
		} catch (CustomeException e) {
			logger.error("Exception occurred in CheckoutService  addCheckout", e);
		}
		if(str!=null) {
			return ResponseEntity.ok().body(str);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Products In Cart");
		//	throw new ProductNotFoundException( "No ProductsIn Cart");	
	}

	// 5. PaymentDone OrderAdd
	@PostMapping("/checkout/payment/{amount}/{paymentWay}")
	public ResponseEntity<?> paymentDone( @PathVariable("amount") Double amount, @PathVariable("paymentWay") String paymentWay)  {
		String str = null;
		try {
			str=checkoutService.orderCompleted( amount, paymentWay);
		} catch (CustomeException e) {
			logger.error("Exception occurred in CartService updateCart", e);
		}
		if(str!="payment_failed") {
			return ResponseEntity.ok().body(str);
		}
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment Failed");
	}

	@DeleteMapping("/checkout/delete/all")
	public ResponseEntity<?> deleteAll()  {
		ResponseEntity<?> tr=null;
		try {
			tr=ResponseEntity.ok(checkoutService.deleteAllCheckout());
		} catch (CustomeException e) {
			logger.error("Exception occurred in CheckOutService deleteAllCheckout", e);
		}
		return tr;
	}

	@GetMapping("/checkout/show")
	public ResponseEntity<?> showCheckout()  {
		ResponseEntity<?> tr=null;
		try {
			List<Checkout> ckList = checkoutService.showCheckout();
			if (ckList.size()<1) {
				tr= ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Products in Checkout");
			} else {
				tr= ResponseEntity.ok().body(ckList);
			}
		} catch (CustomeException e) {
			logger.error("Exception occurred in CheckOutService ShowCheckout", e);
		}
		return tr;
	}

}
