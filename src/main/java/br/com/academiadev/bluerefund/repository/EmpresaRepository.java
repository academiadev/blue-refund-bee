package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.bluerefund.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	
	public Empresa findByCodigo(Integer codigo);
	
	public Empresa findByCodigoAdmin(Integer codigoAdmin);
	
	public Empresa findById(Long id);
	
	public Empresa findByNome(String nome);
	
}
