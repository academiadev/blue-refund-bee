package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academiadev.bluerefund.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByEmail(String email);
	
	public Usuario findById(Long id);

}
