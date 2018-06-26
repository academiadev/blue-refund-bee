package br.com.academiadev.bluerefund.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReembolsoJaAvaliadoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReembolsoJaAvaliadoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReembolsoJaAvaliadoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ReembolsoJaAvaliadoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ReembolsoJaAvaliadoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ReembolsoJaAvaliadoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
