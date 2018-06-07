package br.com.academiadev.bluerefund.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Pra que as classes possam herdar os atributos de pessoa precisamos anotar a
 * classe Pessoa com @MappedSuperclass
 *
 */
@MappedSuperclass
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column
	private String nome;

	@Column(unique=true)
	private String email;

	@Column
	private Integer hashSenha;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa", nullable = false)
	private Empresa empresa; 

	public Pessoa() {
		super();
	}

	public Pessoa(String nome, String email, String senha, Empresa empresa) {
		super();
		this.nome = nome;
		this.email = email;
		this.hashSenha = senha.hashCode();
		this.empresa = empresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getHashSenha() {
		return hashSenha;
	}

	public void setHashSenha(Integer hashSenha) {
		this.hashSenha = hashSenha;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
