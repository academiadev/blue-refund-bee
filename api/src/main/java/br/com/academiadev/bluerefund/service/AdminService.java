package br.com.academiadev.bluerefund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
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
	

}
