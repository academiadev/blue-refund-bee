package br.com.academiadev.bluerefund.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(name = "ultima_troca_de_senha")
	private LocalDateTime ultimaTrocaDeSenha;
	
	@Column
	private String role;

	@ManyToOne(fetch = FetchType.LAZY)
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

	public LocalDateTime getUltimaTrocaDeSenha() {
		return ultimaTrocaDeSenha;
	}

	public void setUltimaTrocaDeSenha(LocalDateTime ultimaTrocaDeSenha) {
		this.ultimaTrocaDeSenha = ultimaTrocaDeSenha;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}