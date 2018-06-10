package br.com.academiadev.bluerefund.converter;

import br.com.academiadev.bluerefund.dto.CategoriaDTO;
import br.com.academiadev.bluerefund.model.Categoria;

public class CategoriaConverter implements Converter<Categoria, CategoriaDTO> {

	@Override
	public CategoriaDTO toDTO(Categoria entity) {
		CategoriaDTO dto = new CategoriaDTO();
		dto.setNome(entity.getNome());
		return dto;
	}

	@Override
	public Categoria toEntity(CategoriaDTO dto) {
		Categoria entity = new Categoria();
		entity.setNome(dto.getNome());
		return null;
	}

}
