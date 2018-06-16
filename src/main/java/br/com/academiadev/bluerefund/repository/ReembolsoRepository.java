package br.com.academiadev.bluerefund.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.bluerefund.model.Reembolso;
import br.com.academiadev.bluerefund.model.Usuario;


@Repository
public interface ReembolsoRepository extends JpaRepository<Reembolso, Long>{

	public Reembolso findById(Long id);
	
	public List<Reembolso> findByUsuario(Usuario usuario);

}
