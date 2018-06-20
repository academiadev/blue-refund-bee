package br.com.academiadev.bluerefund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.dto.EmpresaDTO;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.model.Usuario;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;

@Service
public class EmpresaService implements EmpresaInterface {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Empresa cadastrar(String nome) {
		Empresa empresa = new Empresa(nome);
		
		verificaCodigo(empresa);
		
		this.empresaRepository.save(empresa);
		return empresa;
	}

	private void verificaCodigo(Empresa empresa) {
		while(BuscaCodigo(empresa.getCodigo())) {
			empresa.setNovoCodigo();
		}
	}
	
	private boolean BuscaCodigo(Integer codigo) {
		
		List<Empresa> listaEmpresas = empresaRepository.findAll();
		for (Empresa empresa2 : listaEmpresas) {
			if(empresa2.getCodigo() == codigo ) {
				return true;
			}
		}
		return false;
		
	}
	
	public EmpresaDTO dadosEmpresa() {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		EmpresaDTO dto = new EmpresaDTO();
		
		dto.setId(usuario.getEmpresa().getId());
		dto.setNome(usuario.getEmpresa().getNome());
		dto.setCodEmpregado(usuario.getEmpresa().getCodigo());
		dto.setCodAdmin(usuario.getEmpresa().getCodigoAdmin());
		
		return dto;
	}

}
