package br.com.academiadev.bluerefund.dto;

public class CadastroReembolsoDTO {
	
	private String nome;
	private String categoria;
	private float valorSolicitado;
	private String uploadUrl;
	private String data;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public float getValorSolicitado() {
		return valorSolicitado;
	}
	public void setValorSolicitado(float valorSolicitado) {
		this.valorSolicitado = valorSolicitado;
	}
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
