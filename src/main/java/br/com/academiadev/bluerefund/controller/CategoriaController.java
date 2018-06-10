package br.com.academiadev.bluerefund.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.CategoriaDTO;
import br.com.academiadev.bluerefund.exceptions.CategoriaJaCadastradaException;
import br.com.academiadev.bluerefund.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	
	@PostMapping("/adiciona")
	@ApiOperation(value = "Adiciona uma nova categoria de reembolso")
	public void adiciona(@RequestBody CategoriaDTO dto) throws CategoriaJaCadastradaException {
		categoriaService.adiciona(dto.getNome());
	}
	
	@GetMapping("/buscatodas")
	@ApiOperation(value = "Busca todas categorias cadastradas")
	public List<CategoriaDTO> buscaTodas(){
		
		return categoriaService.buscaTodas();
	}
}
