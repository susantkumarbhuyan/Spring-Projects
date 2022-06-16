package com.bookstore.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.Address;
import com.bookstore.model.Cart;
import com.bookstore.model.Checkout;
import com.bookstore.model.GetEmailId;
import com.bookstore.model.Order;
import com.bookstore.model.User;
import com.bookstore.payload.request.CheckoutRequest;
import com.bookstore.pojo.CurrertDate;
import com.bookstore.repository.CartRepo;
import com.bookstore.repository.CheckoutRepo;
import com.bookstore.repository.EmailIdRepo;
import com.bookstore.repository.OrderRepo;
import com.bookstore.repository.UserRepo;

@Service
public class CheckoutService {
	@Autowired
	private CheckoutRepo checkoutRepo;
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private	CartRepo cartRepo;
	@Autowired
	private	SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private UserRepo userRepository;
	@Autowired
	private EmailIdRepo emailIdRepo;


	public Checkout addCheckout(Address address) throws CustomeException {
		try {
			List<Cart> cart= cartRepo.findAll();
			if(cart.size()>=1) {
				Checkout checkout= new Checkout();
				checkout.setAddress(address);
				checkout.setId(address.getPhoneNo());
				checkout.setCart(cart);		
				Double totalCartPrice= cartRepo.sumPrices();
				checkout.setTotalCartPrice(totalCartPrice);
				return checkoutRepo.save(checkout);
			}
			return null;
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in CheckoutService AddCheckOut", e);
		}
	}

	public String orderCompleted( Double payment, String paymentWay ) throws CustomeException {
		try {
			GetEmailId gt=emailIdRepo.findById(1).get();
			User user = userRepository.findByEmailId(gt.getEmailId()).get();
			CheckoutRequest ct= checkoutRepo.findTotalCartPrice();
			if(payment==ct.getTotalCartPrice()) {
				Order order= new Order();
				order.setOrderDate(new CurrertDate().getDateAndTime());      //Set Current Order Date
				order.setName(user.getFullName());
				order.setEmailId(user.getEmail());
				order.setAmount(payment);
				order.setPaymentWay(paymentWay);	
				order.setId((int) sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
				List<Checkout> ch= checkoutRepo.findAll();
				order.setProducts(ch);
				orderRepo.save(order);
				cartRepo.deleteAll();       //delete Cart items
				checkoutRepo.deleteAll();   //delete checkout items			
				return "Successfully ordered" ;
			}
			return "payment_failed";
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in CheckoutService OrderCompleted", e);
		}
	}

	public String deleteAllCheckout() throws CustomeException {
		try {
			checkoutRepo.deleteAll();
			return "Checkout Deleted";
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in CheckoutService  DeleteAllCheckout", e);
		}
	}

	public List<Checkout> showCheckout() throws CustomeException
	{	try {
		return checkoutRepo.findAll();
	}
	catch(Exception e){
		throw new CustomeException("Exception occurred  in CheckoutService  ShowCheckout", e);
	}

	}



}
