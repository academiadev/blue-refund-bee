package br.com.academiadev.bluerefund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.model.Admin;

@Service
public interface AdminRepository extends JpaRepository<Admin, Long> {

	public Admin findByEmail(String email);
	
	
}
