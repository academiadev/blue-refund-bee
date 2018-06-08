package br.com.academiadev.bluerefund.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.dto.CadastroReembolsoDTO;
import br.com.academiadev.bluerefund.exceptions.CategoriaNaoCadastradaException;
import br.com.academiadev.bluerefund.exceptions.EmpregadoNaoEncontradoException;
import br.com.academiadev.bluerefund.model.Categoria;
import br.com.academiadev.bluerefund.model.Empregado;
import br.com.academiadev.bluerefund.model.Reembolso;
import br.com.academiadev.bluerefund.repository.CategoriaRepository;
import br.com.academiadev.bluerefund.repository.EmpregadoRepository;
import br.com.academiadev.bluerefund.repository.ReembolsoRepository;

@Service
public class ReembolsoService {

	@Autowired
	ReembolsoRepository reembolsoRepository;
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	EmpregadoRepository empregadoRepository;
	
	public void adiciona(CadastroReembolsoDTO dto) throws EmpregadoNaoEncontradoException, CategoriaNaoCadastradaException {
		
		Categoria categoria = categoriaRepository.findByNome(dto.getCategoria());
		Empregado empregado = empregadoRepository.findByEmail(dto.getEmailEmpregado());
		
		validacoesAdiciona(categoria, empregado);
			
		Reembolso reembolso = new Reembolso(dto.getNome(),categoria , new BigDecimal(dto.getValorSolicitado()),
				dto.getUploadUrl(), empregado, LocalDate.now());
		
		reembolsoRepository.save(reembolso);
	}

	private void validacoesAdiciona(Categoria categoria, Empregado empregado)
			throws CategoriaNaoCadastradaException, EmpregadoNaoEncontradoException {
		if(categoria == null)
			throw new CategoriaNaoCadastradaException();
		if(empregado == null)
			throw new EmpregadoNaoEncontradoException();
	}
	
}
