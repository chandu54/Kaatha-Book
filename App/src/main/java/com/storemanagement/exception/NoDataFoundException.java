package com.storemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoDataFoundException extends RuntimeException {

	private static final long serialVersionUID = 3124511439192318476L;

	public NoDataFoundException() {
	}

	public NoDataFoundException(String message) {
		super(message);
	}

}
