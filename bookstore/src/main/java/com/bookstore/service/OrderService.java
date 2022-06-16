package com.bookstore.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.GetEmailId;
import com.bookstore.model.Order;
import com.bookstore.repository.EmailIdRepo;
import com.bookstore.repository.OrderRepo;

@Service
public class OrderService {
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private EmailIdRepo uuiRepo;



	public List<Order> getAllOrders() throws CustomeException {	   //getAllOrders(String uui) 
		try {
			GetEmailId gt=uuiRepo.findById(1).get();
			return 	orderRepo.findAllByEmailId(gt.getEmailId());       //orderRepo.findAllByUui(uui);
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred in OrderService getAllOrders", e);
		}

	}

	public String deleteOrderById(int id) throws CustomeException {
		try {
			if(orderRepo.existsById(id)) {
				orderRepo.deleteById(id);
				return id +" Order Successfully  Deleted";		
			}
			return null;
		} catch (Exception e) {
			throw new CustomeException("Exception occurred in OrderService deleteOrderById", e);
		}

	}

	public String deleteAllOrderByUui() throws CustomeException {
		try {
			GetEmailId gt=uuiRepo.findById(1).get();
			orderRepo.deleteAllByEmailId(gt.getEmailId()); 
			return "All Orders Deleted";
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred in OrderService deleteAllOrderByUui", e);
		}

	}

	public List<Order> getAllOrder() throws CustomeException {
		try {
			return	orderRepo.findAll();
		} catch (Exception e) {
			throw new CustomeException("Exception occurred in OrderService getAllOrder", e);
		}
	}

	public String deleteAllOrder() throws CustomeException {
		try {
			orderRepo.deleteAll();
			return "No Content All Orders Deleted By Admin";
		} catch (Exception e) {
			throw new CustomeException("Exception occurred in OrderService deleteAllOrder", e);
		}
	}

	//	public List<Order> getOrderByDate(String date_from, String date_to) throws CustomeException {
	//		try {
	//			List<Order> orList=	 orderRepo.getOrderByDate(date_from, date_to); 
	//			if( orList.size()>0) {
	//				return orList;
	//			}
	//		return null;
	//
	//		} catch (Exception e) {
	//			throw new CustomeException("Exception occurred in OrderService getOrderByDate", e);
	//		}
	//			}

}
