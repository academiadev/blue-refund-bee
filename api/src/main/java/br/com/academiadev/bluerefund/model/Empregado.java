package br.com.academiadev.bluerefund.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="empregado")
public class Empregado extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "empregado")
	private List<Reembolso> reembolsos;
	
	public Empregado() {

	}

	public Empregado(String nome, String email, String senha, Empresa empresa) {
		super(nome, email, senha, empresa);
	}

	public List<Reembolso> getReembolsos() {
		return reembolsos;
	}

	public void setReembolsos(List<Reembolso> reembolsos) {
		this.reembolsos = reembolsos;
	}

	
}
