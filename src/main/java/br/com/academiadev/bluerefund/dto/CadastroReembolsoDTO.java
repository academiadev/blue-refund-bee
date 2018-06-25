package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class CadastroReembolsoDTO {
	
	@ApiModelProperty(value = "Nome do reembolso", example = "Almoço Garten")
	private String nome;
	@ApiModelProperty(value = "Categoria do reembolso", example = "Alimentação")
	private String categoria;
	@ApiModelProperty(value = "Valor solicitado", example = "25.50")
	private float valorSolicitado;
	@ApiModelProperty(value = "Url do arquivo", example = "server\\imagem.jpg")
	private String uploadUrl;
	@ApiModelProperty(value = "Data do reembolso", example = "1995-11-10")
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
