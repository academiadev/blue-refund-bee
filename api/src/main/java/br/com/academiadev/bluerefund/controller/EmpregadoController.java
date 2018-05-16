package br.com.academiadev.bluerefund.controller;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.academiadev.bluerefund.dto.CadastroEmpregadoDTO;
import br.com.academiadev.bluerefund.exeptions.EmailInvalidoExeption;
import br.com.academiadev.bluerefund.exeptions.EmailJaCadastradoExeption;
import br.com.academiadev.bluerefund.exeptions.EmpresaNaoEncontradaExeption;
import br.com.academiadev.bluerefund.exeptions.SenhaInvalidaExeption;
import br.com.academiadev.bluerefund.service.EmpregadoService;

public class EmpregadoController {
	
	@Autowired
	private EmpregadoService empregadoService;
	
	public void cadastro(CadastroEmpregadoDTO dto) throws SenhaInvalidaExeption, EmailInvalidoExeption, EmailJaCadastradoExeption, EmpresaNaoEncontradaExeption {
		empregadoService.cadastrar(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCodigoEmpresa());
	}

}
