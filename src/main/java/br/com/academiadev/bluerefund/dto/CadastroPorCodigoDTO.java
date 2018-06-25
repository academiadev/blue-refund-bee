package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class CadastroPorCodigoDTO {
	
	@ApiModelProperty(value = "Nome do usu�rio", example = "Jo�o Gabriel")
	private String nome;
	@ApiModelProperty(value = "E-mail do usu�rio", example = "joaogabriel@contaazul.com")
	private String email;
	@ApiModelProperty(value = "Senha do usu�rio", example = "senh4_secreta")
	private String senha;
	@ApiModelProperty(value = "C�digo da empresa", example = "99999999")
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
