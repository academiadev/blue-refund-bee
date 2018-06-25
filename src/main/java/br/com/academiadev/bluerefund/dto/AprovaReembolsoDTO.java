package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class AprovaReembolsoDTO {

	@ApiModelProperty(value = "Id do reembolso", example = "45")
	private Integer id;
	@ApiModelProperty(value = "Valor a ser reembolsado", example = "50")
	private float valorReembolsado;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public float getValorReembolsado() {
		return valorReembolsado;
	}
	public void setValorReembolsado(float valorReembolsado) {
		this.valorReembolsado = valorReembolsado;
	}
	
	
}
