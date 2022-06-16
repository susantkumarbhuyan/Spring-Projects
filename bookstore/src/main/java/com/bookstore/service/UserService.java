package com.bookstore.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.GetEmailId;
import com.bookstore.model.User;
import com.bookstore.repository.EmailIdRepo;
import com.bookstore.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private	UserRepo userRepo;
	@Autowired
	private EmailIdRepo userEmailRepo;

	public List<User> viewUser() throws CustomeException {
		try {
			return userRepo.findAll();
	
		}catch(Exception e){
			throw new CustomeException("Exception occurred  in CartService  findAll", e);
		}
	}
	public String deleteUserByUsername(String username) throws CustomeException {
		try {

			User ur =userRepo.findByUsername(username).get();
			if(ur!=null){ 	
				String name = ur.getFullName();
				userRepo.deleteById(username);
				return name +" User Successfully Deleted";

			}
			return null;
		} catch (Exception e) {
			throw new CustomeException("Exception occurred  in CartService  deleteUserById", e);
		}

	}
	public User viewUserByUsername(String username) throws CustomeException {
		try {
			return	userRepo.findByUsername(username).get();
		} catch (Exception e) {
			throw new CustomeException("Exception occurred in UserService viewUserById", e);
		}	
	}

	public String deleteAllUser() throws CustomeException {
		try {
			userRepo.deleteAll();
			return "All Users Deleted";
		} catch (Exception e) {
			throw new CustomeException("Exception occurred in UserService deleteAllUser", e);
		}
	}

	public User userviewUserDetails() throws CustomeException {
		try {

			GetEmailId gt=userEmailRepo.findById(1).get();
			return userRepo.findByEmailId(gt.getEmailId()).get();

		}catch(Exception e){
			throw new CustomeException("Exception occurred in OrderService userviewUserDetails", e);
		}
	}
	public String updateUserDetail( User user) throws CustomeException {   // user can update only Email, Fullname, Username
		try {
			GetEmailId gt=userEmailRepo.findById(1).get();
			User ud=userRepo.findByEmailId(gt.getEmailId()).get();
			ud.setId(ud.getId());
			ud.setEmailId(ud.getEmail());
			ud.setRoles(ud.getRoles());
			ud.setEmail(user.getEmail());
			ud.setFullName(user.getFullName());
			ud.setUsername(user.getUsername());
			userRepo.save(ud);
			return "Updated Your Details";	
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred in OrderService updateUserDetail", e);
		}
	}
}
