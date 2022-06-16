package com.bookstore.advice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bookstore.exception.NotFoundException;


@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	
	//Validation Exception Adviser
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex)
	{
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
	return errorMap;
	}
	
	
	//Product , User and Order Not Found Exception Adviser
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class )
	public Map<String, String> handleServiceExceptions1(NotFoundException ex)	{
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("ErrorMessage", ex.getMessage());
		return errorMap;
	}
	
}
