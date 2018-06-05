package br.com.academiadev.bluerefund.service;

import java.util.List;

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
		
		Empresa empresa = buscaEmpresaPorCodigo(codigo);
		
		validacoesCadastrar(email, senha, empresa);
		
		Empregado empregado = new Empregado(nome, email, senha, empresa);
		empregadoRepository.save(empregado);
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
	
	public String novaSenha(String senhaAntiga1,String senhaAntiga2, String email)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException {
		
		Empregado empregado = empregadoRepository.findByEmail(email);
		validacoesNovaSenha(senhaAntiga1, senhaAntiga2, email, empregado);
		
		String senha = new SenhaService().novaSenha();
		
//		empregadoRepository.delete(empregado);
		empregado.setHashSenha(senha.hashCode());
		empregadoRepository.save(empregado);
		
		return senha;
	}

	private void validacoesNovaSenha(String senhaAntiga1, String senhaAntiga2, String email, Empregado empregado)
			throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException {
		//Validações
		if(!senhaAntiga1.equals(senhaAntiga2)) {
			throw new SenhasDiferentesException();
		}
		
		if(empregado == null) {
			throw new EmailNaoEncontradoException();
		}
		if(senhaAntiga1.hashCode() != empregado.getHashSenha()) {
			throw new SenhaIncorretaException();
		}
	}
}
