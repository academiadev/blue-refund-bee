package br.com.academiadev.bluerefund.service;

import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.dto.RoleDTO;
import br.com.academiadev.bluerefund.dto.UsuarioDTO;
import br.com.academiadev.bluerefund.model.Usuario;

@Service
public class UsuarioService {
	
	public UsuarioDTO dadosUsuario(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setEmail(usuario.getEmail());
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		
		return dto;
	}
	
	public RoleDTO roleUsuario(Usuario usuario){

		
		RoleDTO dto = new RoleDTO();
		dto.setRole(usuario.getAutorizacoes().get(0).getNome());
		
		return dto;
	}
}
