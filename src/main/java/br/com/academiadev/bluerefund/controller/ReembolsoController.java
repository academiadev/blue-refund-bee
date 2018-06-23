package br.com.academiadev.bluerefund.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("Reembolso")
@RestController
@RequestMapping("/reembolso")
public class ReembolsoController {
	
	@Autowired
	ReembolsoService reembolsoService;
	
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@PostMapping("/adiciona")
	@ApiOperation(value = "Adiciona um novo reembolso ao funcionário")
	@PreAuthorize("hasRole('USER')")
	public void adiciona(@RequestBody CadastroReembolsoDTO dto) 
			throws EmpregadoNaoEncontradoException, CategoriaNaoCadastradaException {
		reembolsoService.adiciona(dto);
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@GetMapping("/buscaPorEmpregado")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation("Busca todos reembolsos de um empregado")
	public List<ReembolsoDTO> buscaPorEmpregado() throws EmailNaoEncontradoException {
		return reembolsoService.buscaPorEmpregado();
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@GetMapping("/buscaPorEmpresa")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation("Busca todos reembolsos dos empregados de uma empresa")
	public List<ReembolsoDTO> buscaPorEmpresa() throws EmpresaNaoEncontradaException {
		return reembolsoService.buscaPorEmpresa();
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@PostMapping("/exclui")
	@ApiOperation("Exclui um reembolso pelo ID")
	@PreAuthorize("hasRole('USER')")
	public void exclui(@RequestBody Integer id) throws ReembolsoNaoEncontradoException {
		reembolsoService.exclui((long) id);
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@PostMapping("/reprova")
	@ApiOperation("Muda o status de um reembolso para reprovado pelo seu id")
	@PreAuthorize("hasRole('ADMIN')")
	public void reprova(@RequestBody Integer id) throws ReembolsoNaoEncontradoException {
		reembolsoService.reprova((long) id);
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@PostMapping("/aprova")
	@ApiOperation("Aprova um reembolso com o seu id. se o valor reembolsado for 0, ele é integralmente reembolsado,"
			+ " se não, é reembolsado o valor definido")
	@PreAuthorize("hasRole('ADMIN')")
	public void aprova(@RequestBody AprovaReembolsoDTO dto) throws ReembolsoNaoEncontradoException, ValorInvalidoException {
		reembolsoService.aprova((long) dto.getId(), new BigDecimal(dto.getValorReembolsado()));
	}
}
