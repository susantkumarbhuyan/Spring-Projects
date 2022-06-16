package com.bookstore.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class LoginRequest {
	@Size(min = 3, max = 20)
  @NotBlank (message = "username Shouldn't Black")
  private String username;
  @NotBlank  (message = "Invalid Password")
  @Size(min = 6, max = 40)
  private String password;
}
