package br.com.academiadev.bluerefund.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.NovaSenhaDTO;
import br.com.academiadev.bluerefund.dto.RecuperaSenhaDTO;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.exceptions.SenhasDiferentesException;
import br.com.academiadev.bluerefund.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Admin")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

//	@ApiOperation(value = "Cadastra um admin e uma empresa")
//	@PostMapping("/cadastro")
//	public void cadastro(@RequestBody CadastroAdminDTO cadastroAdminDTO) 
//			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
//		adminService.cadastrar(cadastroAdminDTO.getNome(), cadastroAdminDTO.getEmail(), cadastroAdminDTO.getSenha(), cadastroAdminDTO.getNomeEmpresa());
//	}
	
	@ApiOperation(value = "Troca a senha do usuário")
	@PostMapping("/novasenha")
	public void novaSenha(@RequestBody NovaSenhaDTO dto)
			throws EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException, SenhasDiferentesException {
		adminService.novaSenha(dto.getSenhaAntiga(), dto.getNovaSenha(), dto.getEmail());
	}
	
	@ApiOperation(value = "Troca a senha do usuário e a envia por e-mail")
	@PostMapping("/recuperasenha")
	public void recuperaSenha(@RequestBody RecuperaSenhaDTO dto) throws EmailNaoEncontradoException, MessagingException {
		adminService.recuperaSenha(dto.getEmail());
	}
}
