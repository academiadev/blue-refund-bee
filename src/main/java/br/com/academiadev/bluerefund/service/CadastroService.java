package br.com.academiadev.bluerefund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.dto.CadastroPorCodigoDTO;
import br.com.academiadev.bluerefund.dto.LoginDTO;
import br.com.academiadev.bluerefund.exceptions.CodigosInconsistentesException;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.model.Usuario;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;

@Service
public class CadastroService {

	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private EmpregadoService empregadoService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void cadastroComCodigo(CadastroPorCodigoDTO dto) 
			throws CodigosInconsistentesException, SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException {
		Empresa empresa1 = empresaRepository.findByCodigo(dto.getEmpresa());
		Empresa empresa2 = empresaRepository.findByCodigoAdmin(dto.getEmpresa());
		
		if(empresa1 != null && empresa2 != null) 
			throw new CodigosInconsistentesException();
		
		if(empresa1 != null && empresa2 == null) {
			empregadoService.cadastrar(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getEmpresa());
			return;
		}
		
		if(empresa1 == null)
			adminService.cadastrarPorCodigo(dto, empresa2);
		
	}
	
	public void excluiCadastro(LoginDTO dto) throws EmailInvalidoException, SenhaIncorretaException {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		
		if(!dto.getEmail().equals(usuario.getEmail())) {
			throw new EmailInvalidoException("E-mail inválido");
		}
		
		if(!passwordEncoder.matches(dto.getSenha(), usuario.getHashSenha())) {
			throw new SenhaIncorretaException("Senha incorreta");
		}
		usuario.setReembolsos(null);
		usuarioRepository.save(usuario);
		usuarioRepository.delete(usuario);
	}
	
}
