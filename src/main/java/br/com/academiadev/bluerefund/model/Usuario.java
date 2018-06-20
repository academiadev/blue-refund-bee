package br.com.academiadev.bluerefund.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.academiadev.bluerefund.model.Autorizacao;


@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column
	private String nome;

	@Column(unique=true)
	private String email;

	@JsonIgnore
	@Column
	private String hashSenha;
	
	@Column(name = "ultima_troca_de_senha")
	private LocalDateTime ultimaTrocaDeSenha;

	@ManyToOne(fetch = FetchType.EAGER)
	private Empresa empresa; 
	
	@OneToMany(mappedBy = "usuario",orphanRemoval=true)
	private List<Reembolso> reembolsos;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_autorizacao", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "autorizacao_id", referencedColumnName = "id"))
	private List<Autorizacao> autorizacoes;

	public Usuario() {
		super();
	}
	
	public Usuario(String nome, String email, String senha, Empresa empresa) {
		super();
		this.nome = nome;
		this.email = email;
		this.hashSenha = senha;
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

	public String getHashSenha() {
		return hashSenha;
	}

	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}



	public List<Reembolso> getReembolsos() {
		return reembolsos;
	}



	public void setReembolsos(List<Reembolso> reembolsos) {
		this.reembolsos = reembolsos;
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
	
	
	public List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(List<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.autorizacoes;
	}


	@JsonIgnore
	@Override
	public String getPassword() {
		return this.hashSenha.toString();
	}


	@JsonIgnore
	@Override
	public String getUsername() {
		return this.email;
	}


	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
}
