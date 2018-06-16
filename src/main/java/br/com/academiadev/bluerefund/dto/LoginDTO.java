package br.com.academiadev.bluerefund.dto;


public class LoginDTO {

	private String email;
	private String senha;
	
	public String getUsuario() {
		return email;
	}
	public void setUsuario(String usuario) {
		this.email = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	
}
