package br.com.academiadev.bluerefund.model;

import java.math.BigDecimal;

public class Reembolso {
	
	private Long id;
	private String nome;
	private Categoria categoria;
	private StatusReembolso status;
	private BigDecimal valorSolicitado;
	private BigDecimal valorReembolsado;
	private Upload upload;
	private Empregado empregado;
	
	public Reembolso(String nome, Categoria categoria, BigDecimal valorSolicitado,
			 Upload upload, Empregado empregado) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.status = StatusReembolso.AGUARDANDO;
		this.valorSolicitado = valorSolicitado;
		this.valorReembolsado = new BigDecimal(0);
		this.upload = upload;
		this.empregado = empregado;
	}
	
	public Reembolso(String nome, Categoria categoria, BigDecimal valorSolicitado,
			  Empregado empregado) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.status = StatusReembolso.AGUARDANDO;
		this.valorSolicitado = valorSolicitado;
		this.valorReembolsado = new BigDecimal(0);
		this.upload = null;
		this.empregado = empregado;
	}
	
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
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public StatusReembolso getStatus() {
		return status;
	}
	public void setStatus(StatusReembolso status) {
		this.status = status;
	}
	public BigDecimal getValorSolicitado() {
		return valorSolicitado;
	}
	public void setValorSolicitado(BigDecimal valorSolicitado) {
		this.valorSolicitado = valorSolicitado;
	}
	public BigDecimal getValorReembolsado() {
		return valorReembolsado;
	}
	public void setValorReembolsado(BigDecimal valorReembolsado) {
		this.valorReembolsado = valorReembolsado;
	}
	public Upload getUpload() {
		return upload;
	}
	public void setUpload(Upload upload) {
		this.upload = upload;
	}
	public Empregado getEmpregado() {
		return empregado;
	}
	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}
	
	

}
