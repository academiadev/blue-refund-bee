package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class LoginDTO {

	@ApiModelProperty(value = "E-mail do usu�rio", example = "joaodasilva@contaazul.com")
	private String email;
	@ApiModelProperty(value = "Senha do usu�rio", example = "senh4_secreta")
	private String senha;
	
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

	
}
