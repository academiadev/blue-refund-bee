package br.com.academiadev.bluerefund.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="reembolso")
public class Reembolso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	@Column
	private String nome;
	@Column
	private LocalDate data;
	private transient Categoria categoria;
	@Column
	private StatusReembolso status;
	@Column
	private BigDecimal valorSolicitado;
	@Column
	private BigDecimal valorReembolsado;
	private transient Upload upload;
	private transient Empregado empregado;
	
	public Reembolso() {

	}
	
	public Reembolso(String nome, Categoria categoria, BigDecimal valorSolicitado,
			 Upload upload, Empregado empregado, LocalDate data) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.status = StatusReembolso.AGUARDANDO;
		this.valorSolicitado = valorSolicitado;
		this.valorReembolsado = new BigDecimal(0);
		this.upload = upload;
		this.empregado = empregado;
		this.data = data;
	}
	
	public Reembolso(String nome, Categoria categoria, BigDecimal valorSolicitado,
			  Empregado empregado, LocalDate data) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.status = StatusReembolso.AGUARDANDO;
		this.valorSolicitado = valorSolicitado;
		this.valorReembolsado = new BigDecimal(0);
		this.upload = null;
		this.empregado = empregado;
		this.data = data;
	}
	
	
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
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
