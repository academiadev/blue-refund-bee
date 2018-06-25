package br.com.academiadev.bluerefund.dto;

public class CadastroAdminDTO {

	private String nome;
	private String email;
	private String senha;
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
