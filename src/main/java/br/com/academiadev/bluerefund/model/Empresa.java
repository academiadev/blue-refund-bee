package br.com.academiadev.bluerefund.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="empresa")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	@Column
	private String nome;
	@Column
	private Integer codigo;
	@Column
	private Integer codigoAdmin;
	
	@OneToMany(mappedBy = "empresa")
	private List<Usuario> usuarios;
		
	public Empresa() {

	}
	
	public Empresa(String nome) {
		super();
		this.nome = nome;
		String cod = nome + Math.random();
		String codAdm = cod + id;
		this.codigo = cod.hashCode();
		this.codigoAdmin = codAdm.hashCode();
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
	
	public Integer getCodigoAdmin() {
		return codigoAdmin;
	}

	public void setCodigoAdmin(Integer codigoAdmin) {
		this.codigoAdmin = codigoAdmin;
	}

	public void setNovoCodigo() {
		String cod = this.nome + Math.random();
		this.codigo = cod.hashCode();
	}
	
	public void setNovoCodigoAdmin() {
		String cod = this.nome + id + Math.random();
		this.codigo = cod.hashCode();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
	

}
