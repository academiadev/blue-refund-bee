package br.com.academiadev.bluerefund.dto;

public class EmpresaDTO {

	private Long id;
	private String nome;
	private Integer codEmpregado;
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
