package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class EditaReembolsoDTO {
	
	@ApiModelProperty(value = "Id do reembolso", example = "25")
	private Integer id;
	@ApiModelProperty(value = "Nome do reembolso", example = "Almoço Garten")
	private String nome;
	@ApiModelProperty(value = "Data do reembolso", example = "1995-11-10")
	private String data;
	@ApiModelProperty(value = "Categoria do reembolso", example = "Alimentação")
	private String categoria;
	@ApiModelProperty(value = "Valor Solicitado pelo empregado", example = "25.50")
	private float valorSolicitado;
	@ApiModelProperty(value = "Url do arquivo", example = "server\\imagem.jpg")
	private String uploadUrl;
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
	
	

}
