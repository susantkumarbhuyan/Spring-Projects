package com.bookstore.payload.request;
import java.util.List;

import com.bookstore.model.Address;
import com.bookstore.model.Cart;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckoutRequest {
	private String id;
	private List<Cart> cart;
	private Address address;
	private double totalCartPrice;


}
