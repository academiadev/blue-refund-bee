package br.com.academiadev.bluerefund.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends Pessoa {

	private static final long serialVersionUID = 1L;

	/**
	 * É essencial sempre termos um construtor vazio pro JPA utilizar ;)
	 */
	public Admin() {

	}

	public Admin(String nome, String email, String senha, Empresa empresa) {
		super(nome, email, senha, empresa);
	}
}
