package br.com.academiadev.bluerefund.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.converter.ReembolsoConverter;
import br.com.academiadev.bluerefund.dto.CadastroReembolsoDTO;
import br.com.academiadev.bluerefund.dto.ReembolsoDTO;
import br.com.academiadev.bluerefund.exceptions.CategoriaNaoCadastradaException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpregadoNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.ReembolsoNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.ValorInvalidoException;
import br.com.academiadev.bluerefund.model.Categoria;
import br.com.academiadev.bluerefund.model.Empregado;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.model.Reembolso;
import br.com.academiadev.bluerefund.model.StatusReembolso;
import br.com.academiadev.bluerefund.repository.CategoriaRepository;
import br.com.academiadev.bluerefund.repository.EmpregadoRepository;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;
import br.com.academiadev.bluerefund.repository.ReembolsoRepository;

@Service
public class ReembolsoService {

	@Autowired
	ReembolsoRepository reembolsoRepository;
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	EmpregadoRepository empregadoRepository;
	@Autowired
	EmpresaRepository empresaRepository;
	
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
	
	public List<ReembolsoDTO> buscaPorEmpregado(String email) throws EmailNaoEncontradoException{
		Empregado empregado = empregadoRepository.findByEmail(email);
		
		if(empregado==null)
			throw new EmailNaoEncontradoException();
			
		List<Reembolso> reembolsos = reembolsoRepository.findByEmpregado(empregado);
		List<ReembolsoDTO> dtos = new ArrayList<>();
		
		for (Reembolso reembolso : reembolsos) {
			dtos.add(new ReembolsoConverter().toDTO(reembolso));
		}
		return dtos;
	}
	
	public void exclui(Long id) throws ReembolsoNaoEncontradoException{
		Reembolso reembolso = reembolsoRepository.findById(id);
		
		if(reembolso == null)
			throw new ReembolsoNaoEncontradoException();
		
		reembolsoRepository.delete(reembolso);
		
	}
	
	public void reprova(Long id) throws ReembolsoNaoEncontradoException {
		Reembolso reembolso = reembolsoRepository.findById(id);
		
		if(reembolso == null)
			throw new ReembolsoNaoEncontradoException();
		
		reembolso.setStatus(StatusReembolso.REPROVADO);
		reembolsoRepository.save(reembolso);
	}
	
	public void aprova(Long id, BigDecimal valorReembolsado) throws ReembolsoNaoEncontradoException, ValorInvalidoException {
		Reembolso reembolso = reembolsoRepository.findById(id);
		
		if(reembolso == null)
			throw new ReembolsoNaoEncontradoException();
		
		if(valorReembolsado.compareTo(reembolso.getValorSolicitado()) == 1)
			throw new ValorInvalidoException();
		
		if(valorReembolsado.equals(new BigDecimal(0))) {
			reembolso.setValorReembolsado(reembolso.getValorSolicitado());
		}else {
			reembolso.setValorReembolsado(valorReembolsado);
		}
		
		reembolso.setStatus(StatusReembolso.APROVADO);
		reembolsoRepository.save(reembolso);
	}
	
	public List<ReembolsoDTO> buscaPorEmpresa(Integer codigo) throws EmpregadoNaoEncontradoException{
		
		Empresa empresa = empresaRepository.findByCodigo(codigo);
		
		if(empresa == null)
			throw new EmpregadoNaoEncontradoException();
		
		List<Empregado> empregados = empresa.getEmpregados();
		List<Reembolso> reembolsos = new ArrayList<>();
		for (Empregado empregado : empregados) {
			reembolsos.addAll(reembolsoRepository.findByEmpregado(empregado));
		}

		List<ReembolsoDTO> dtos = new ArrayList<>();
		
		for (Reembolso reembolso : reembolsos) {
			dtos.add(new ReembolsoConverter().toDTO(reembolso));
		}
		return dtos;
	}
	
	
	
}
