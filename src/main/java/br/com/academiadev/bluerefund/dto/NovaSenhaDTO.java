package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class NovaSenhaDTO {

	@ApiModelProperty(value = "Senha do usuário", example = "senh4_secreta")
	private String senhaAntiga;
	@ApiModelProperty(value = "Senha do usuário", example = "nov4_senha")
	private String novaSenha;
	@ApiModelProperty(value = "E-mail do usuário", example = "joaodasilva@contaazul.com")
	private String email;
	
	public String getSenhaAntiga() {
		return senhaAntiga;
	}
	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
