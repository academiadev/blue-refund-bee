package br.com.academiadev.bluerefund.controller;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.exeptions.SenhaInvalidaExeption;
import br.com.academiadev.bluerefund.service.AdminService;
import br.com.academiadev.bluerefund.exeptions.EmailInvalidoExeption;
import br.com.academiadev.bluerefund.exeptions.EmailJaCadastradoExeption;

public class AdminController {
	
	@Autowired
	private AdminService adminService;

	public void cadastro(CadastroAdminDTO cadastroAdminDTO) 
			throws SenhaInvalidaExeption, EmailInvalidoExeption, EmailJaCadastradoExeption {
		adminService.cadastrar(cadastroAdminDTO.getNome(), cadastroAdminDTO.getEmail(), cadastroAdminDTO.getSenha(), cadastroAdminDTO.getNomeEmpresa());
	}
}
