package br.com.academiadev.bluerefund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.dto.CadastroPorCodigoDTO;
import br.com.academiadev.bluerefund.exceptions.CodigosInconsistentesException;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;

@Service
public class CadastroService {

	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	EmpregadoService empregadoService;
	@Autowired
	AdminService adminService;
	
	public void cadastroComCodigo(CadastroPorCodigoDTO dto) 
			throws CodigosInconsistentesException, SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException {
		Empresa empresa1 = empresaRepository.findByCodigo(dto.getCodigoEmpresa());
		Empresa empresa2 = empresaRepository.findByCodigoAdmin(dto.getCodigoEmpresa());
		
		if(empresa1 != null && empresa2 != null) 
			throw new CodigosInconsistentesException();
		
		if(empresa1 != null && empresa2 == null) {
			empregadoService.cadastrar(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCodigoEmpresa());
			return;
		}
		
		if(empresa1 == null)
			adminService.cadastrarPorCodigo(dto, empresa2);
		
		
		
	}
	
}
