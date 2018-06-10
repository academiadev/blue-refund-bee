package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.bluerefund.model.Empregado;


@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {
	
	public Empregado findByEmail(String email);
	
	public Empregado findById(Long id);
	
}
