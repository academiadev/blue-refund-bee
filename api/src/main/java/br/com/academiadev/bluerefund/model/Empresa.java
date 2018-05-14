package br.com.academiadev.bluerefund.model;

public class Empresa {
	
	private Long id;
	private String nome;
	private Integer codigo;
	
	public Empresa(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		this.codigo = nome.hashCode();
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
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	

}
