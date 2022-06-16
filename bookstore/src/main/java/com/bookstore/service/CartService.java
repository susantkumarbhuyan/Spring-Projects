package com.bookstore.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.Cart;
import com.bookstore.model.Product;
import com.bookstore.repository.CartRepo;
import com.bookstore.repository.ProductRepo;

@Service
public class CartService {
	@Autowired
	private CartRepo cartRepo;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private	SequenceGeneratorService sequenceGeneratorService;



	public List<Cart> showCart() throws CustomeException {
		try {
			return cartRepo.findAll();	
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in CartService  showCart", e);
		}
	}

	public String addCart(int id, int quantity) throws CustomeException  {
		try {
			Product product = productRepo.findById(id).get();		
			if(product!=null) {
				if(quantity>=1) {
					Cart cart = new Cart();
					cart.setProduct(product);
					cart.setId((int) sequenceGeneratorService.generateSequence(Cart.SEQUENCE_NAME));
					cart.setQuantity(quantity);
					Double totalPrice = (double) (quantity * product.getPrice());
					cart.setTotalPrice(totalPrice);
					cartRepo.save(cart);
					return  id + " Successfull added to cart";
				} else {
					return " Quantity must be 1 or more";
				}	
			}
			return null;
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in CartService  addCart", e);
		}
	}

	public String deleteCart(int id) // throws CustomeException 
	{
		try {
				cartRepo.deleteById(id);
				return "Successfully Deleted";
			//	return  id +" Product not FOund";
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in CartService  deleteCart", e);
		}

	}

	public String deleteAllCart() throws CustomeException {
		try {
			cartRepo.deleteAll();
			return "Successfully All Items Deleted";
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in CartService  deleteAllCart", e);
		}
	}

	public String updateCart(int id , int quantity)  throws CustomeException {


		try {
			Cart pr=cartRepo.findById(id).get();
			if(pr!=null )
			{
				if(quantity>=1) {
					Cart cr = new Cart();
					cr.setId(pr.getId());
					cr.setProduct(pr.getProduct());
					Double totalPrice = (double) (quantity * pr.getProduct().getPrice()); 
					cr.setTotalPrice(totalPrice);
					cr.setQuantity(quantity);
					cartRepo.save(cr);
					return "Successfully Updated";
				}
				else {	
					return " Quantity must be 1 or more";
				}		
			}	
			return null;
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in CartService  updateCart", e);
		}
	}
}
