package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academiadev.bluerefund.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
