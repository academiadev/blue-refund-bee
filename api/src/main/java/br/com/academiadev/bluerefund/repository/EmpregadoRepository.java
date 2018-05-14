package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academiadev.bluerefund.model.Empregado;

public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {

}
