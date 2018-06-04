package br.com.academiadev.bluerefund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.CadastroEmpregadoDTO;
import br.com.academiadev.bluerefund.exeptions.EmailInvalidoExeption;
import br.com.academiadev.bluerefund.exeptions.EmailJaCadastradoExeption;
import br.com.academiadev.bluerefund.exeptions.EmpresaNaoEncontradaExeption;
import br.com.academiadev.bluerefund.exeptions.SenhaInvalidaExeption;
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
			throws SenhaInvalidaExeption, EmailInvalidoExeption, EmailJaCadastradoExeption, EmpresaNaoEncontradaExeption {
		empregadoService.cadastrar(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCodigoEmpresa());
	}

}
