package br.com.academiadev.bluerefund.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SenhasDiferentesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SenhasDiferentesException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SenhasDiferentesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SenhasDiferentesException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SenhasDiferentesException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SenhasDiferentesException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
