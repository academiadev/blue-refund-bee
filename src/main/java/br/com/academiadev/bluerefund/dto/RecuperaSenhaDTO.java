package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class RecuperaSenhaDTO {

	@ApiModelProperty(value = "E-mail do usuário", example = "joaodasilva@contaazul.com")
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
