package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academiadev.bluerefund.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
