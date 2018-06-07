package br.com.academiadev.bluerefund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Admin")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@ApiOperation(value = "Cadastra um admin e uma empresa")
	@PostMapping("/cadastro")
	public void cadastro(@RequestBody CadastroAdminDTO cadastroAdminDTO) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		adminService.cadastrar(cadastroAdminDTO.getNome(), cadastroAdminDTO.getEmail(), cadastroAdminDTO.getSenha(), cadastroAdminDTO.getNomeEmpresa());
	}
}
