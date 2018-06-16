package br.com.academiadev.bluerefund.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Usuario implements Serializable {

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
	
	@OneToMany(mappedBy = "usuario")
	private List<Reembolso> reembolsos;

	public Usuario() {
		super();
	}
	
	

	public Usuario(String nome, String email, String senha, String role, Empresa empresa) {
		super();
		this.nome = nome;
		this.email = email;
		this.hashSenha = senha.hashCode();
		this.role = role;
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
