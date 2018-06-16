package br.com.academiadev.bluerefund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.dto.CadastroPorCodigoDTO;
import br.com.academiadev.bluerefund.exceptions.CodigosInconsistentesException;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.service.AdminService;
import br.com.academiadev.bluerefund.service.CadastroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Cadastro")
@RestController
@RequestMapping("/cadastro")
public class CadastroController {
	
	@Autowired
	CadastroService cadastroService;
	@Autowired
	AdminService adminService;
	
	@ApiOperation(value = "Cadastra um admin ou empregado pelo código da empresa")
	@PostMapping("/porcodigo")
	public void porCodigo(@RequestBody CadastroPorCodigoDTO dto) throws CodigosInconsistentesException, SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException {
		cadastroService.cadastroComCodigo(dto);
	}
	
	@ApiOperation(value = "Cadastra um admin e uma empresa")
	@PostMapping("/admineempresa")
	public void adminEEmpresa(@RequestBody CadastroAdminDTO cadastroAdminDTO) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		adminService.cadastrarComEmpresa(cadastroAdminDTO.getNome(), cadastroAdminDTO.getEmail(), cadastroAdminDTO.getSenha(), cadastroAdminDTO.getNomeEmpresa());
	}

}
