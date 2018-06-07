package br.com.academiadev.bluerefund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.CadastroEmpregadoDTO;
import br.com.academiadev.bluerefund.dto.NovaSenhaDTO;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.exceptions.SenhasDiferentesException;
import br.com.academiadev.bluerefund.service.EmpregadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Empregado")
@RestController
@RequestMapping("/empregado")
public class EmpregadoController {
	
	@Autowired
	private EmpregadoService empregadoService;
	
	@ApiOperation(value = "Cadastra um empregado e o relaciona com uma empresa")
	@PostMapping("/cadastro")
	public void cadastro(@RequestBody CadastroEmpregadoDTO dto) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException {
		empregadoService.cadastrar(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCodigoEmpresa());
	}
	
	@ApiOperation(value = "Gera uma nova senha aleatória")
	@PostMapping("/novasenha")
	public String novaSenha(@RequestBody NovaSenhaDTO dto)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException {
		
		return empregadoService.novaSenha(dto.getSenha1(), dto.getSenha2(), dto.getEmail());
	}

}
