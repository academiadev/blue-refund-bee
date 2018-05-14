package br.com.academiadev.bluerefund.model;

public class Empregado extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Empregado(String nome, String email, String senha, Empresa empresa) {
		super(nome, email, senha, empresa);
	}

	
}
