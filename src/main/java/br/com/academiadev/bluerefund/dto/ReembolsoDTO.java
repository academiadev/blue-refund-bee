package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class ReembolsoDTO {
	
	@ApiModelProperty(value = "Id do reembolso", example = "25")
	private Integer id;
	@ApiModelProperty(value = "Nome do reembolso", example = "Almoço Garten")
	private String nome;
	@ApiModelProperty(value = "Data do reembolso", example = "10/11/1995")
	private String data;
	@ApiModelProperty(value = "Categoria do reembolso", example = "Alimentação")
	private String categoria;
	@ApiModelProperty(value = "Status do reembolso", example = "Aprovado")
	private String status;
	@ApiModelProperty(value = "Valor Solicitado pelo empregado", example = "25.50")
	private float valorSolicitado;
	@ApiModelProperty(value = "Valor reembolsado pelo admin", example = "20.00")
	private float valorReembolsado;
	@ApiModelProperty(value = "Url do arquivo", example = "server\\imagem.jpg")
	private String urlupload;
	@ApiModelProperty(value = "E-mail do usuário", example = "joaogabriel@contaazul.com")
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
