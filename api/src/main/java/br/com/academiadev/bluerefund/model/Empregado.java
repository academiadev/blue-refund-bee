package br.com.academiadev.bluerefund.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="empregado")
public class Empregado extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	public Empregado() {

	}

	public Empregado(String nome, String email, String senha, Empresa empresa) {
		super(nome, email, senha, empresa);
	}

	
}
