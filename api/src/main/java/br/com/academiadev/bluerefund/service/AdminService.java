package br.com.academiadev.bluerefund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	public void cadastrar(String nome, String email, String senha, String nomeEmpresa){
		//validar email
		//Buscar se não tem um admin com o mesmo email
		//Buscar se não tem um empregado co o mesmo email
		//Buscar se já existe esta empresa, se não existe, cadastra-la
		//Verificar se a senha tem 8 caracteres com caracteres especiais e numeros
		
		Empresa empresa = new Empresa(nomeEmpresa);
		Admin admin = new Admin(nome, email, senha, empresa);
		
		adminRepository.save(admin);
	}
	

}
