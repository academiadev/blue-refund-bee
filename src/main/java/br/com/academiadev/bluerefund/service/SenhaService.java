package br.com.academiadev.bluerefund.service;

import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.exceptions.SenhasDiferentesException;
import br.com.academiadev.bluerefund.model.Usuario;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;

@Service
public class SenhaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String alfabeto = "abcdefghijklmnopqrstuvwxyz";
	
	public String novaSenha() {
		String senha = "";
		Random gerador = new Random();
		senha += alfabeto.toUpperCase().charAt(gerador.nextInt(alfabeto.length()));
		for(int i=0; i<3; i++)
			senha += alfabeto.charAt(gerador.nextInt(alfabeto.length()));
		senha += "_";
		for(int i=0; i<3; i++)
			senha += Integer.toString(gerador.nextInt(10));
		return senha;
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

}
