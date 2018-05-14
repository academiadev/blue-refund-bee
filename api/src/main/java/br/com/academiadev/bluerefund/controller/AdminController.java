package br.com.academiadev.bluerefund.controller;

import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.service.AdminService;

public class AdminController {

	public void cadastro(CadastroAdminDTO cadastroAdminDTO) {
		new AdminService().cadastrar(cadastroAdminDTO.getNome(), cadastroAdminDTO.getEmail(), cadastroAdminDTO.getSenha(), cadastroAdminDTO.getNomeEmpresa());
	}
}
