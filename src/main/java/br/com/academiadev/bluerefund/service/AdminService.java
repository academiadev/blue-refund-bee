package br.com.academiadev.bluerefund.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.config.jwt.ApiPasswordEncoder;
import br.com.academiadev.bluerefund.dto.CadastroPorCodigoDTO;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.model.Autorizacao;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.model.Usuario;
import br.com.academiadev.bluerefund.repository.AutorizacaoRepository;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;

@Service
public class AdminService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private ApiPasswordEncoder passwordEncoder;
	@Autowired
	private AutorizacaoRepository autorizacaoRepository;
	
	@Transactional
	public void cadastrarComEmpresa(String nome, String email, String senha, String nomeEmpresa) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException{
		
		validacoesCadastrar(email, senha);
		
		
		
		Usuario usuario = new Usuario(nome, email, passwordEncoder.encode(senha), null);
		Autorizacao autorizacao = autorizacaoRepository.findByNome("ROLE_ADMIN");
		usuario.setAutorizacoes(new ArrayList<Autorizacao>());
		usuario.getAutorizacoes().add(autorizacao);
		Empresa empresa = empresaService.cadastrar(nomeEmpresa);
		usuario.setEmpresa(empresa);
		
		usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void cadastrarPorCodigo(CadastroPorCodigoDTO dto, Empresa empresa ) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException{
		
		validacoesCadastrar(dto.getEmail(), dto.getSenha());
		
		if(empresa == null)
			throw new EmpresaNaoEncontradaException("Empresa não encontrada");
		
		Usuario usuario = new Usuario(dto.getNome(), dto.getEmail(), passwordEncoder.encode(dto.getSenha()), empresa);
		Autorizacao autorizacao = autorizacaoRepository.findByNome("ROLE_ADMIN");
		usuario.setAutorizacoes(new ArrayList<Autorizacao>());
		usuario.getAutorizacoes().add(autorizacao);
		
		usuarioRepository.save(usuario);
	}
	
	

	private void validacoesCadastrar(String email, String senha)
			throws EmailInvalidoException, EmailJaCadastradoException, SenhaInvalidaException {
		boolean validaEmail = new EmailService().validarEmail(email);
		
		boolean emailJaCadastrado = verificaEmailCadastrado(email);
		
		boolean validaSenha = validaSenha(senha);
		
		if(!validaEmail)
			throw new EmailInvalidoException("E-mail inválido");
		
		if(emailJaCadastrado)
			throw new EmailJaCadastradoException("E-mail já cadastrado");
		
		if(!validaSenha) 
			throw new SenhaInvalidaException("Senha inválida, a senha deve ter pelo menos 8 caracteres, sendo 1 especial e 1 numerico");
	}
	
	private boolean validaSenha(String senha) {
		if(senha.length() < 8)
			return false;
		
		boolean caracterEspecial = false;
		boolean caracterNumerico = false;
		for(int i=0; i<senha.length(); i++) {
			if( ((int)senha.charAt(i) >= 32 && (int)senha.charAt(i) <= 47) || (int)senha.charAt(i) == 95 ) 
				caracterEspecial = true;
			if( (int)senha.charAt(i) >= 48 && (int)senha.charAt(i) <= 57  )
				caracterNumerico = true;
		}
		
		if(caracterEspecial == false || caracterNumerico == false)
			return false;
		
		return true;
	}
	
	private boolean verificaEmailCadastrado(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if(usuario != null)
			return true;
		
		return false;
	}
	

	

}
