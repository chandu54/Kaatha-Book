package com.storemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class AppExceptionController {

	@ExceptionHandler(value = NoDataFoundException.class)
	public ResponseEntity<String> exception(NoDataFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler(value = AlreadyExistsException.class)
	public ResponseEntity<String> exception(AlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
	}
}
