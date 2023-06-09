package com.storemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -1847386498427908644L;

	public AlreadyExistsException() {
	}

	public AlreadyExistsException(String message) {
		super(message);
	}

}
