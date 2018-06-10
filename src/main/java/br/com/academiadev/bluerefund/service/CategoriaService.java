package br.com.academiadev.bluerefund.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.converter.CategoriaConverter;
import br.com.academiadev.bluerefund.dto.CategoriaDTO;
import br.com.academiadev.bluerefund.exceptions.CategoriaJaCadastradaException;
import br.com.academiadev.bluerefund.model.Categoria;
import br.com.academiadev.bluerefund.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	public List<CategoriaDTO> buscaTodas(){
		List<CategoriaDTO> dtos = new ArrayList<CategoriaDTO>();
		List<Categoria> categorias = categoriaRepository.findAll();
		for (Categoria categoria : categorias) {
			dtos.add(new CategoriaConverter().toDTO(categoria));
		}
		
		return dtos;
	}
	
	public void adiciona(String nome) throws CategoriaJaCadastradaException {
		Categoria categoria = categoriaRepository.findByNome(nome);
		
		if(categoria != null)
			throw new CategoriaJaCadastradaException();
			
		categoriaRepository.save(new Categoria(nome));
	}
}
