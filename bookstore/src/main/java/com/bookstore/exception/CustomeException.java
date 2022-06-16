package com.bookstore.exception;
public class CustomeException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CustomeException(String message, Throwable e) {
		super(message, e);
	}

}