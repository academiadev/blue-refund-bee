package br.com.academiadev.bluerefund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.CadastroReembolsoDTO;
import br.com.academiadev.bluerefund.exceptions.CategoriaNaoCadastradaException;
import br.com.academiadev.bluerefund.exceptions.EmpregadoNaoEncontradoException;
import br.com.academiadev.bluerefund.service.ReembolsoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Reembolso")
@RestController
@RequestMapping("/reembolso")
public class ReembolsoController {
	
	@Autowired
	ReembolsoService reembolsoService;
	
	@PostMapping("/adiciona")
	@ApiOperation(value = "Adiciona um novo reembolso ao funcionário")
	public void adiciona(@RequestBody CadastroReembolsoDTO dto) 
			throws EmpregadoNaoEncontradoException, CategoriaNaoCadastradaException {
		reembolsoService.adiciona(dto);
	}
}
