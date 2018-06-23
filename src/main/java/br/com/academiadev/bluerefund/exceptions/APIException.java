package br.com.academiadev.bluerefund.exceptions;

import org.springframework.http.HttpStatus;

public abstract class APIException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String errorKey;
	private HttpStatus status;

	protected APIException(String message, String errorKey, HttpStatus status) {
		super(message);
		this.errorKey = errorKey;
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getErrorKey() {
		return errorKey;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}
