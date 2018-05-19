package br.com.academiadev.bluerefund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.academiadev.bluerefund.exeptions.EmailInvalidoExeption;
import br.com.academiadev.bluerefund.exeptions.EmailJaCadastradoExeption;
import br.com.academiadev.bluerefund.exeptions.EmpresaNaoEncontradaExeption;
import br.com.academiadev.bluerefund.exeptions.SenhaInvalidaExeption;
import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.model.Empregado;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.AdminRepository;
import br.com.academiadev.bluerefund.repository.EmpregadoRepository;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;

public class EmpregadoService {
	
	@Autowired
	private EmpregadoRepository empregadoRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public void cadastrar(String nome, String email, String senha, Integer codigo) 
			throws SenhaInvalidaExeption, EmailInvalidoExeption, EmailJaCadastradoExeption, EmpresaNaoEncontradaExeption{
		
		Empresa empresa = buscaEmpresaPorCodigo(codigo);
		
		validacoesCadastrar(email, senha, empresa);
		
		
		Admin admin = new Admin(nome, email, senha, empresa);
		
		adminRepository.save(admin);
	}
	
	private Empresa buscaEmpresaPorCodigo(Integer codigo) {
		List<Empresa> empresas = empresaRepository.findAll();
		for (Empresa empresa : empresas) {
			if(empresa.getCodigo().equals(codigo))
				return empresa;
		}
		return null;
	}
	
	
	private void validacoesCadastrar(String email, String senha, Empresa empresa)
			throws EmailInvalidoExeption, EmailJaCadastradoExeption, SenhaInvalidaExeption, EmpresaNaoEncontradaExeption {
		boolean validaEmail = new EmailService().validarEmail(email);
		
		boolean emailJaCadastrado = verificaEmailCadastrado(email);
		
		boolean validaSenha = validaSenha(senha);
		
		if(!validaEmail)
			throw new EmailInvalidoExeption();
		
		if(emailJaCadastrado)
			throw new EmailJaCadastradoExeption();
		
		if(!validaSenha) 
			throw new SenhaInvalidaExeption();
		
		if(empresa == null) {
			throw new EmpresaNaoEncontradaExeption();
		}
	
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
