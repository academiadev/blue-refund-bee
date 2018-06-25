package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class CadastroPorCodigoDTO {
	
	@ApiModelProperty(value = "Nome do usuário", example = "João Gabriel")
	private String nome;
	@ApiModelProperty(value = "E-mail do usuário", example = "joaogabriel@contaazul.com")
	private String email;
	@ApiModelProperty(value = "Senha do usuário", example = "senh4_secreta")
	private String senha;
	@ApiModelProperty(value = "Código da empresa", example = "99999999")
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
