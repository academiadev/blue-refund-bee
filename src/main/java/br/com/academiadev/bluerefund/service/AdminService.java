package br.com.academiadev.bluerefund.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.exceptions.SenhasDiferentesException;
import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.model.Empregado;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.AdminRepository;
import br.com.academiadev.bluerefund.repository.EmpregadoRepository;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private EmpregadoRepository empregadoRepository;
	@Autowired
	private EmpresaService empresaService;
	
	public void cadastrar(String nome, String email, String senha, String nomeEmpresa) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException{
		
		validacoesCadastrar(email, senha);
		
		
		Empresa empresa = empresaService.cadastrar(nomeEmpresa);
		Admin admin = new Admin(nome, email, senha, empresa);
		
		adminRepository.save(admin);
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
		
		Admin admin = adminRepository.findByEmail(email);
		validacoesNovaSenha(senhaAntiga1, novaSenha, email, admin);
		
		admin.setHashSenha(novaSenha.hashCode());
		adminRepository.save(admin);
	}

	private void validacoesNovaSenha(String senhaAntiga1, String novaSenha, String email, Admin admin)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException {
		//Validações
		if(!validaSenha(novaSenha)) {
			throw new SenhaInvalidaException();
		}
		
		if(admin == null) {
			throw new EmailNaoEncontradoException();
		}
		if(senhaAntiga1.hashCode() != admin.getHashSenha()) {
			throw new SenhaIncorretaException();
		}
	}
	
	public void recuperaSenha(String email) throws EmailNaoEncontradoException, MessagingException {
		Admin admin = adminRepository.findByEmail(email);
		
		if(admin == null)
			throw new EmailNaoEncontradoException();
		
		String novaSenha = new SenhaService().novaSenha();
		admin.setHashSenha(novaSenha.hashCode());
		new EmailService().enviaEmail(email, novaSenha, admin.getNome());
		adminRepository.save(admin);
	}
	

}
