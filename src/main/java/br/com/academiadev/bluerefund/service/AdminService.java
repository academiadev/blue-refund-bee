package br.com.academiadev.bluerefund.service;

import java.util.ArrayList;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.dto.CadastroPorCodigoDTO;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.exceptions.SenhasDiferentesException;
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
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AutorizacaoRepository autorizacaoRepository;
	
	public void cadastrarComEmpresa(String nome, String email, String senha, String nomeEmpresa) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException{
		
		validacoesCadastrar(email, senha);
		
		
		Empresa empresa = empresaService.cadastrar(nomeEmpresa);
		Usuario usuario = new Usuario(nome, email, passwordEncoder.encode(senha), empresa);
		Autorizacao autorizacao = autorizacaoRepository.findByNome("ROLE_ADMIN");
		usuario.setAutorizacoes(new ArrayList<Autorizacao>());
		usuario.getAutorizacoes().add(autorizacao);
		
		usuarioRepository.save(usuario);
	}
	
	public void cadastrarPorCodigo(CadastroPorCodigoDTO dto, Empresa empresa ) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException{
		
		validacoesCadastrar(dto.getEmail(), dto.getSenha());
		
		if(empresa == null)
			throw new EmpresaNaoEncontradaException();
		
		Usuario usuario = new Usuario(dto.getNome(), dto.getEmail(), passwordEncoder.encode(dto.getSenha()), empresa);
		Autorizacao autorizacao = autorizacaoRepository.findByNome("ROLE_ADMIN");
		usuario.getAutorizacoes().add(autorizacao);
		
		usuarioRepository.save(usuario);
	}
	
	

	private void validacoesCadastrar(String email, String senha)
			throws EmailInvalidoException, EmailJaCadastradoException, SenhaInvalidaException {
		boolean validaEmail = new EmailService().validarEmail(email);
		
		boolean emailJaCadastrado = verificaEmailCadastrado(email);
		
		boolean validaSenha = validaSenha(senha);
		
		if(!validaEmail)
			throw new EmailInvalidoException();
		
		if(emailJaCadastrado)
			throw new EmailJaCadastradoException();
		
		if(!validaSenha) 
			throw new SenhaInvalidaException();
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
	
	public void novaSenha(String senhaAntiga,String novaSenha, String email)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException {
		
		Usuario usuario = usuarioRepository.findByEmail(email);
		validacoesNovaSenha(senhaAntiga, novaSenha, email, usuario);
		
		usuario.setHashSenha(passwordEncoder.encode(novaSenha));
		usuarioRepository.save(usuario);
	}

	private void validacoesNovaSenha(String senhaAntiga, String novaSenha, String email, Usuario usuario)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException {

		if(!validaSenha(novaSenha)) {
			throw new SenhaInvalidaException();
		}
		
		if(usuario == null) {
			throw new EmailNaoEncontradoException();
		}
		if(!passwordEncoder.matches(senhaAntiga, usuario.getHashSenha())) {
			throw new SenhaIncorretaException();
		}
	}
	
	public void recuperaSenha(String email) throws EmailNaoEncontradoException, MessagingException {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if(usuario == null)
			throw new EmailNaoEncontradoException();
		
		String novaSenha = new SenhaService().novaSenha();
		usuario.setHashSenha(passwordEncoder.encode(novaSenha));
		new EmailService().enviaEmail(email, novaSenha, usuario.getNome());
		usuarioRepository.save(usuario);
	}
	

}
