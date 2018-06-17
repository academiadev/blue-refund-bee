package br.com.academiadev.bluerefund.dto;

public class AdminDTO {
	
	private Long id;
	private String nome;
	private String email;
	private Integer codEmpresa;
	private Integer codEmpresaAdmin;
	
	
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
	public Integer getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(Integer codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	public Integer getCodEmpresaAdmin() {
		return codEmpresaAdmin;
	}
	public void setCodEmpresaAdmin(Integer codEmpresaAdmin) {
		this.codEmpresaAdmin = codEmpresaAdmin;
	}
	
}
