package br.com.academiadev.bluerefund.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.AprovaReembolsoDTO;
import br.com.academiadev.bluerefund.dto.CadastroReembolsoDTO;
import br.com.academiadev.bluerefund.dto.ReembolsoDTO;
import br.com.academiadev.bluerefund.exceptions.CategoriaNaoCadastradaException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpregadoNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.ReembolsoNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.ValorInvalidoException;
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
	
	@GetMapping("/buscaPorEmpregado")
	@ApiOperation("Busca todos reembolsos de um empregado")
	public List<ReembolsoDTO> buscaPorEmpregado(@RequestParam Integer id) throws EmailNaoEncontradoException {
		return reembolsoService.buscaPorEmpregado(id);
	}
	
	@GetMapping("/buscaPorEmpresa")
	@ApiOperation("Busca todos reembolsos dos empregados de uma empresa")
	public List<ReembolsoDTO> buscaPorEmpresa(@RequestParam Integer id) throws EmpresaNaoEncontradaException {
		return reembolsoService.buscaPorEmpresa(id);
	}
	
	
	@PostMapping("/exclui")
	@ApiOperation("Exclui um reembolso pelo ID")
	public void exclui(@RequestBody Integer id) throws ReembolsoNaoEncontradoException {
		reembolsoService.exclui((long) id);
	}
	
	@PostMapping("/reprova")
	@ApiOperation("Muda o status de um reembolso para reprovado pelo seu id")
	public void reprova(@RequestBody Integer id) throws ReembolsoNaoEncontradoException {
		reembolsoService.reprova((long) id);
	}
	
	@PostMapping("/aprova")
	@ApiOperation("Aprova um reembolso com o seu id. se o valor reembolsado for 0, ele é integralmente reembolsado,"
			+ " se não é reembolsado o valor definido")
	public void aprova(@RequestBody AprovaReembolsoDTO dto) throws ReembolsoNaoEncontradoException, ValorInvalidoException {
		reembolsoService.aprova((long) dto.getId(), new BigDecimal(dto.getValorReembolsado()));
	}
}
