package br.com.academiadev.bluerefund.dto;

public class ReembolsoDTO {
	
	private Integer id;
	private String nome;
	private String data;
	private String categoria;
	private String status;
	private float valorSolicitado;
	private float valorReembolsado;
	private String urlupload;
	private String emailEmpregado;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getValorSolicitado() {
		return valorSolicitado;
	}
	public void setValorSolicitado(float valorSolicitado) {
		this.valorSolicitado = valorSolicitado;
	}
	public float getValorReembolsado() {
		return valorReembolsado;
	}
	public void setValorReembolsado(float valorReembolsado) {
		this.valorReembolsado = valorReembolsado;
	}
	public String getUrlupload() {
		return urlupload;
	}
	public void setUrlupload(String urlupload) {
		this.urlupload = urlupload;
	}
	public String getEmailEmpregado() {
		return emailEmpregado;
	}
	public void setEmailEmpregado(String emailEmpregado) {
		this.emailEmpregado = emailEmpregado;
	}
	
	
	
}
