package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class EmpresaDTO {

	@ApiModelProperty(value = "Id da empresa", example = "20")
	private Long id;
	@ApiModelProperty(value = "Nome da empresa", example = "ContaAzul")
	private String nome;
	@ApiModelProperty(value = "Código para cadastrar empregado da empresa", example = "99999999")
	private Integer codEmpregado;
	@ApiModelProperty(value = "Código para cadastrar admin da empresa", example = "-99999999")
	private Integer codAdmin;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getCodEmpregado() {
		return codEmpregado;
	}
	public void setCodEmpregado(Integer codEmpregado) {
		this.codEmpregado = codEmpregado;
	}
	public Integer getCodAdmin() {
		return codAdmin;
	}
	public void setCodAdmin(Integer codAdmin) {
		this.codAdmin = codAdmin;
	}

	
}
