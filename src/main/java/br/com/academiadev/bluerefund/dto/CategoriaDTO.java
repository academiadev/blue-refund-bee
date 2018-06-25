package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class CategoriaDTO {

	@ApiModelProperty(value = "Nome da categoria", example = "Transporte")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
