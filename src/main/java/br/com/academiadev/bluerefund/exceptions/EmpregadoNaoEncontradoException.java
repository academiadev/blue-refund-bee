package br.com.academiadev.bluerefund.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmpregadoNaoEncontradoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmpregadoNaoEncontradoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpregadoNaoEncontradoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public EmpregadoNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EmpregadoNaoEncontradoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EmpregadoNaoEncontradoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
