package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academiadev.bluerefund.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

}
