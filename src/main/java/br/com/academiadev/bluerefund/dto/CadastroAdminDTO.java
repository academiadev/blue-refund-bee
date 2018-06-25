	package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class CadastroAdminDTO {

	@ApiModelProperty(value = "Nome do usuário", example = "João da Silva")
	private String nome;
	@ApiModelProperty(value = "E-mail do usuário", example = "joaodasilva@contaazul.com")
	private String email;
	@ApiModelProperty(value = "Senha do usuário", example = "senh4_secreta")
	private String senha;
	@ApiModelProperty(value = "Nome da empresa a ser cadastrada", example = "Conta Azul")
	private String empresa;

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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String nomeEmpresa) {
		this.empresa = nomeEmpresa;
	}

}
