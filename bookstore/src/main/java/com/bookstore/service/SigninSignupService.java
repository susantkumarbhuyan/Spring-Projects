package com.bookstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.ERole;
import com.bookstore.model.GetEmailId;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.payload.request.LoginRequest;
import com.bookstore.payload.request.SignupRequest;
import com.bookstore.payload.response.MessageResponse;
import com.bookstore.payload.response.UserInfoResponse;
import com.bookstore.repository.*;
import com.bookstore.security.service.UserDetailsImpl;


@Service
public class SigninSignupService {
	@Autowired
	private	UserRepo userRepository;

	@Autowired
	private	PasswordEncoder encoder;

	@Autowired
	private	RoleRepo roleRepository;

	@Autowired
	private	AuthenticationManager authenticationManager;

	@Autowired
	private EmailIdRepo emailIdRepo;

	public ResponseEntity<?> signIn( LoginRequest loginRequest)
	{
		try {
			User ur=userRepository.findByUsername(loginRequest.getUsername()).get();
			if(ur!=null) {
				boolean result = encoder.matches(loginRequest.getPassword(), ur.getPassword());
				if(result) {
					Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

					SecurityContextHolder.getContext().setAuthentication(authentication);

					UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

					List<String> roles = userDetails.getAuthorities().stream()
							.map(item -> item.getAuthority())
							.collect(Collectors.toList());

					GetEmailId str=new GetEmailId();
					str.setId(1);
					str.setEmailId(userDetails.getEmail());
					emailIdRepo.save(str);
					//	String	uui=userDetails.getEmail();
					//	System.out.print(uui);
					return  ResponseEntity
							.status(HttpStatus.OK)
							.body(new UserInfoResponse( userDetails.getId(),
									userDetails.getUsername(),
									userDetails.getEmail(),
									roles));	
				}
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("Wrong Password!"));
			}
			return null;
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred in  signinSignupService SignIn", e);
		}

	}

	public ResponseEntity<?> signUp(SignupRequest signUpRequest) throws CustomeException {
		try {
			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Username is already taken!"));
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse(" Email is already in use!"));
			}

			// Create new user's account
			User user = new User(signUpRequest.getFullName(), signUpRequest.getUsername(), 
					signUpRequest.getEmail(),
					encoder.encode(signUpRequest.getPassword()));
			user.setEmailId(signUpRequest.getEmail());
			Set<String> strRoles = signUpRequest.getRoles();
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
			}

			user.setRoles(roles);
			userRepository.save(user);
			return  ResponseEntity
					.status(HttpStatus.OK)
					.body("User registered successfully!");	
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred in  signinSignupService SignUp", e);
		}

	}
}
