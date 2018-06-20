package br.com.academiadev.bluerefund.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.converter.ReembolsoConverter;
import br.com.academiadev.bluerefund.dto.CadastroReembolsoDTO;
import br.com.academiadev.bluerefund.dto.ReembolsoDTO;
import br.com.academiadev.bluerefund.exceptions.CategoriaNaoCadastradaException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpregadoNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.ReembolsoNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.ValorInvalidoException;
import br.com.academiadev.bluerefund.model.Categoria;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.model.Reembolso;
import br.com.academiadev.bluerefund.model.StatusReembolso;
import br.com.academiadev.bluerefund.model.Usuario;
import br.com.academiadev.bluerefund.repository.CategoriaRepository;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;
import br.com.academiadev.bluerefund.repository.ReembolsoRepository;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;

@Service
public class ReembolsoService {

	@Autowired
	ReembolsoRepository reembolsoRepository;
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	EmpresaRepository empresaRepository;
	
	public void adiciona(CadastroReembolsoDTO dto) throws EmpregadoNaoEncontradoException, CategoriaNaoCadastradaException {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		
		Categoria categoria = categoriaRepository.findByNome(dto.getCategoria());

		validacoesAdiciona(categoria, usuario);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Reembolso reembolso = new Reembolso(dto.getNome(),categoria , new BigDecimal(dto.getValorSolicitado()),
				dto.getUploadUrl(), usuario, LocalDate.parse(dto.getData(), formatter));
		
		reembolsoRepository.save(reembolso);
	}

	private void validacoesAdiciona(Categoria categoria, Usuario usuario)
			throws CategoriaNaoCadastradaException, EmpregadoNaoEncontradoException {
		if(categoria == null)
			throw new CategoriaNaoCadastradaException();
		if(usuario == null)
			throw new EmpregadoNaoEncontradoException();
	}
	
	public List<ReembolsoDTO> buscaPorEmpregado() throws EmailNaoEncontradoException{
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		
		if(usuario==null)
			throw new EmailNaoEncontradoException();
			
		List<Reembolso> reembolsos = reembolsoRepository.findByUsuario(usuario);
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
	
	public List<ReembolsoDTO> buscaPorEmpresa() throws EmpresaNaoEncontradaException{
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario_token = usuarioRepository.findByEmail(email_token);
		Empresa empresa = usuario_token.getEmpresa();
		
		if(empresa == null)
			throw new EmpresaNaoEncontradaException();
		
		List<Usuario> usuarios = empresa.getUsuarios();
		List<Reembolso> reembolsos = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			reembolsos.addAll(reembolsoRepository.findByUsuario(usuario));
		}

		List<ReembolsoDTO> dtos = new ArrayList<>();
		
		for (Reembolso reembolso : reembolsos) {
			dtos.add(new ReembolsoConverter().toDTO(reembolso));
		}
		return dtos;
	}
	
	
	
}
