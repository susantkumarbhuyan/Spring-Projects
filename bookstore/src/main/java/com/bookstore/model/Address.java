package com.bookstore.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	@NotBlank(message = "Address Required")
	private String address;
	@NotBlank (message = "City is Required")
	private String city;
	@NotBlank(message = "State is Required")
	private String state;
	@Pattern(regexp = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$", message = "enter Correct Pin Code")
	private String pin;
	@Pattern(regexp = "^[789]\\d{9}$", message = "Invalid Mobile number entered")
	private String phoneNo;
}
