package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academiadev.bluerefund.model.Autorizacao;

public interface AutorizacaoRepository extends JpaRepository<Autorizacao, Long> {

	public Autorizacao findByNome(String nome);
	
}
