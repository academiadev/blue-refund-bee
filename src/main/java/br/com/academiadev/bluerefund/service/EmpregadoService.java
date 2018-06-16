package br.com.academiadev.bluerefund.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import br.com.academiadev.bluerefund.repository.EmpresaRepository;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;

@Service
public class EmpregadoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	AutorizacaoRepository autorizacaoRepository;
	
	public void cadastrar(String nome, String email, String senha, Integer codigo) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException{
		
		Empresa empresa = empresaRepository.findByCodigo(codigo);
		
		validacoesCadastrar(email, senha, empresa);
		
		Usuario usuario = new Usuario(nome, email, passwordEncoder.encode(senha), empresa);
		Autorizacao autorizacao = autorizacaoRepository.findByNome("ROLE_USER");
		usuario.getAutorizacoes().add(autorizacao);
		
		usuarioRepository.save(usuario);
	}
	
	private void validacoesCadastrar(String email, String senha, Empresa empresa)
			throws EmailInvalidoException, EmailJaCadastradoException, SenhaInvalidaException, EmpresaNaoEncontradaException {
		boolean validaEmail = new EmailService().validarEmail(email);
		
		boolean emailJaCadastrado = verificaEmailCadastrado(email);
		
		boolean validaSenha = validaSenha(senha);
		
		if(!validaEmail)
			throw new EmailInvalidoException();
		
		if(emailJaCadastrado)
			throw new EmailJaCadastradoException();
		
		if(!validaSenha) 
			throw new SenhaInvalidaException();
		
		if(empresa == null) {
			throw new EmpresaNaoEncontradaException();
		}
	
	}
	
	private boolean validaSenha(String senha) {
		if(senha.length() < 8)
			return false;
		
		boolean caracterEspecial = false;
		boolean caracterNumerico = false;
		for(int i=0; i<senha.length(); i++) {
			if( ((int)senha.charAt(i) >= 32 && (int)senha.charAt(i) <= 47) || senha.charAt(i) == 95 ) 
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
	
	public void novaSenha(String senhaAntiga1,String novaSenha, String email)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException {
		
		Usuario usuario = usuarioRepository.findByEmail(email);
		validacoesNovaSenha(senhaAntiga1, novaSenha, email, usuario);
		
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
