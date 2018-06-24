package br.com.academiadev.bluerefund.dto;

public class CadastroPorCodigoDTO {
	
	private String nome;
	private String email;
	private String senha;
	private Integer empresa;
	
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Integer getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Integer codigoEmpresa) {
		this.empresa = codigoEmpresa;
	}
	
	

}
