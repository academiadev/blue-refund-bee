package br.com.academiadev.bluerefund.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.dto.UsuarioDTO;
import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.dto.CadastroPorCodigoDTO;
import br.com.academiadev.bluerefund.dto.EmpresaDTO;
import br.com.academiadev.bluerefund.dto.LoginDTO;
import br.com.academiadev.bluerefund.dto.NovaSenhaDTO;
import br.com.academiadev.bluerefund.dto.RecuperaSenhaDTO;
import br.com.academiadev.bluerefund.dto.RoleDTO;
import br.com.academiadev.bluerefund.exceptions.CodigosInconsistentesException;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.exceptions.SenhaTrocadaRecentementeException;
import br.com.academiadev.bluerefund.exceptions.SenhasDiferentesException;
import br.com.academiadev.bluerefund.model.Usuario;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;
import br.com.academiadev.bluerefund.service.AdminService;
import br.com.academiadev.bluerefund.service.CadastroService;
import br.com.academiadev.bluerefund.service.EmpresaService;
import br.com.academiadev.bluerefund.service.SenhaService;
import br.com.academiadev.bluerefund.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("Usuario")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private SenhaService senhaService;
	@Autowired
	private CadastroService cadastroService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@ApiOperation(value = "Troca a senha do usuário")
	@PostMapping("/novasenha")
	public void novaSenha(@RequestBody NovaSenhaDTO dto)
			throws EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException, SenhasDiferentesException, EmailInvalidoException, SenhaTrocadaRecentementeException {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		
		senhaService.novaSenha(dto.getSenhaAntiga(), dto.getNovaSenha(), dto.getEmail(), usuario);
	}
	
	@ApiOperation(value = "Troca a senha do usuário e a envia por e-mail")
	@PostMapping("/recuperasenha")
	public void recuperaSenha(@RequestBody RecuperaSenhaDTO dto) throws EmailNaoEncontradoException, MessagingException {
		senhaService.recuperaSenha(dto.getEmail());
	}
	
	@ApiOperation(value = "Cadastra um admin ou empregado pelo código da empresa")
	@PostMapping("/cadastro/porcodigo")
	public void porCodigo(@RequestBody CadastroPorCodigoDTO dto) throws CodigosInconsistentesException, SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, EmpresaNaoEncontradaException {
		cadastroService.cadastroComCodigo(dto);
	}
	
	@ApiOperation(value = "Cadastra um admin e uma empresa")
	@PostMapping("/cadastro/admineempresa")
	public void adminEEmpresa(@RequestBody CadastroAdminDTO cadastroAdminDTO) 
			throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		adminService.cadastrarComEmpresa(cadastroAdminDTO.getNome(), cadastroAdminDTO.getEmail(), cadastroAdminDTO.getSenha(), cadastroAdminDTO.getNomeEmpresa());
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@ApiOperation(value = "Exclui um usuário")
	@PostMapping("/excluir")
	public void exclui(@RequestBody LoginDTO loginDTO) throws EmailInvalidoException, SenhaIncorretaException  {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		
		cadastroService.excluiCadastro(loginDTO, usuario);
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@ApiOperation(value = "Busca dados do usuário")
	@PostMapping("/dadosUsuario")
	public UsuarioDTO dadosUsuario(){
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		
		return usuarioService.dadosUsuario(usuario);
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@ApiOperation(value = "Busca dados da empresa referente ao admin")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/dadosEmpresaAdmin")
	public EmpresaDTO dadosEmpresaAdmin(){
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		
		return empresaService.dadosEmpresa(usuario);
	}
	
	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@ApiOperation(value = "Retorna a role do usuário")
	@PostMapping("/role")
	public RoleDTO role() {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email_token = currentUser.getName();
		Usuario usuario = usuarioRepository.findByEmail(email_token);
		
		return usuarioService.roleUsuario(usuario);
	}
	
	
}
