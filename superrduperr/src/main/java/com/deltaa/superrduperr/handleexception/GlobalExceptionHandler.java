package com.deltaa.superrduperr.handleexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
		//Custom ExceptionHandler
	
		@ExceptionHandler
		public ResponseEntity<ToDoListCustomError> handleException(ToDoListCustomException exc) {
			
			ToDoListCustomError error = new ToDoListCustomError();
			error.setErrorcode(HttpStatus.NOT_FOUND.value());
			error.setMessage(exc.getMessage());
			error.setTimestamp(System.currentTimeMillis());

			return new ResponseEntity<ToDoListCustomError>(error,HttpStatus.NOT_FOUND);
			
		}
		
		
		

}
