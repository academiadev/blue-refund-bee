package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class UsuarioDTO {
	
	@ApiModelProperty(value = "Id do usuário", example = "10")
	private Long id;
	@ApiModelProperty(value = "Nome do usuário", example = "João Gabriel")
	private String nome;
	@ApiModelProperty(value = "E-mail do usuário", example = "joaogabriel@contaazul.com")
	private String email;	
	
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
	
}
