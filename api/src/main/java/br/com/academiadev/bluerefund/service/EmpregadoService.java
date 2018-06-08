package br.com.academiadev.bluerefund.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.exceptions.SenhasDiferentesException;
import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.model.Empregado;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.AdminRepository;
import br.com.academiadev.bluerefund.repository.EmpregadoRepository;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;

@Service
public class EmpregadoService {
	
	@Autowired
	private EmpregadoRepository empregadoRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public void cadastrar(String nome, String email, String senha, Integer codigo) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException{
		
		Empresa empresa = empresaRepository.findByCodigo(codigo);
		
		validacoesCadastrar(email, senha, empresa);
		
		Empregado empregado = new Empregado(nome, email, senha, empresa);
		empregadoRepository.save(empregado);
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
		List<Admin> admins = adminRepository.findAll();
		for (Admin admin : admins) {
			if(admin.getEmail().equals(email))
				return true;
		}
		
		List<Empregado> empregados = empregadoRepository.findAll();
		for (Empregado empregado : empregados) {
			if(empregado.getEmail().equals(email))
				return true;
		}
		return false;
	}
	
	public void novaSenha(String senhaAntiga1,String novaSenha, String email)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException {
		
		Empregado empregado = empregadoRepository.findByEmail(email);
		validacoesNovaSenha(senhaAntiga1, novaSenha, email, empregado);
		
		empregado.setHashSenha(novaSenha.hashCode());
		empregadoRepository.save(empregado);
	}

	private void validacoesNovaSenha(String senhaAntiga1, String novaSenha, String email, Empregado empregado)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException {
		//Validações
		if(!validaSenha(novaSenha)) {
			throw new SenhaInvalidaException();
		}
		
		if(empregado == null) {
			throw new EmailNaoEncontradoException();
		}
		if(senhaAntiga1.hashCode() != empregado.getHashSenha()) {
			throw new SenhaIncorretaException();
		}
	}
	
	public void recuperaSenha(String email) throws EmailNaoEncontradoException, MessagingException {
		Empregado empregado = empregadoRepository.findByEmail(email);
		
		if(empregado == null)
			throw new EmailNaoEncontradoException();
		
		String novaSenha = new SenhaService().novaSenha();
		empregado.setHashSenha(novaSenha.hashCode());
		new EmailService().enviaEmail(email, novaSenha, empregado.getNome());
		empregadoRepository.save(empregado);
	}
}
