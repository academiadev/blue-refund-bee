package br.com.academiadev.bluerefund;

import java.io.IOException;
import java.nio.charset.Charset;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.academiadev.bluerefund.Util.InstanciasParaTestes;
import br.com.academiadev.bluerefund.controller.AutenticacaoController;
import br.com.academiadev.bluerefund.controller.UsuarioController;
import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.dto.CadastroPorCodigoDTO;
import br.com.academiadev.bluerefund.dto.LoginDTO;
import br.com.academiadev.bluerefund.dto.RoleDTO;
import br.com.academiadev.bluerefund.dto.TokenDTO;
import br.com.academiadev.bluerefund.exceptions.CodigosInconsistentesException;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
public class BluerefundApplicationTests {
	
	/*
	 * Para estes testes funcionarem corretamente é necessário que:
	 * - O banco tenha somente:
	 * INSERT INTO AUTORIZACAO (id, nome) VALUES (1, 'ROLE_USER');
	 * INSERT INTO AUTORIZACAO (id, nome) VALUES (2, 'ROLE_ADMIN');
	 * - Os testes sejam executados em ordem
	 */
	
	@Autowired
	private UsuarioController usuarioController;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private AutenticacaoController autenticacaoController;
	
	@Autowired
	private MockMvc mockMvc;
	
	private String currentToken;
	
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
	
	
	@Test
	public void T1cadastraAdminEEmpresaEmailInvalido() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		CadastroAdminDTO dto = new CadastroAdminDTO();
		dto.setEmail("emailErrado");
		dto.setNome("Admin1");
		dto.setEmpresa("Empresa1");
		dto.setSenha("senha_admin1");
		
		try {
			usuarioController.adminEEmpresa(dto);
		} catch (EmailInvalidoException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.assertTrue(false);
	}
	
	@Test
	public void T2cadastraAdminEEmpresaSenhaInvalida() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		CadastroAdminDTO dto = new CadastroAdminDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setNome("Admin1");
		dto.setEmpresa("Empresa1");
		dto.setSenha("senhaErrada");
		
		try {
			usuarioController.adminEEmpresa(dto);
		} catch (SenhaInvalidaException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.assertTrue(false);
	}
	
	@Test
	public void T3cadastraAdminEEmpresa() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		CadastroAdminDTO dto = new CadastroAdminDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setNome("Admin1");
		dto.setEmpresa("Empresa1");
		dto.setSenha("minha_senha1");
		usuarioController.adminEEmpresa(dto);
	}
	
	@Test
	public void T4cadastraAdminEEmpresaEmailJaCadastrado() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		CadastroAdminDTO dto = new CadastroAdminDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setNome("Admin1");
		dto.setEmpresa("Empresa1");
		dto.setSenha("minha_senha1");
		
		try {
			usuarioController.adminEEmpresa(dto);
		} catch (EmailJaCadastradoException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.assertTrue(false);
	}
	
	@Test
	public void T5cadastraPorCodigoEmpresaNaoEncontrada() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setEmpresa(0);
		dto.setNome("empregado1");
		dto.setSenha("minha_senha1");
		
		try {
			usuarioController.porCodigo(dto);
		} catch (EmpresaNaoEncontradaException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.assertTrue(false);
	}
	
	@Test
	public void T6cadastraPorCodigoSenhaInvalida() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setEmpresa(empresaRepository.findByNome("Empresa1").getCodigo());
		dto.setNome("empregado1");
		dto.setSenha("senhaErrada");
		
		try {
			usuarioController.porCodigo(dto);
		} catch (SenhaInvalidaException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.assertTrue(false);
	}
	
	@Test
	public void T7cadastraPorCodigoEmailJaCadastrado() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setEmpresa(empresaRepository.findByNome("Empresa1").getCodigo());
		dto.setNome("empregado1");
		dto.setSenha("minha_senha1");
		
		try {
			usuarioController.porCodigo(dto);
		} catch (EmailJaCadastradoException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.assertTrue(false);
	}
	
	@Test
	public void T8cadastraPorCodigo() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setEmpresa(empresaRepository.findByNome("Empresa1").getCodigo());
		dto.setNome("empregado1");
		dto.setSenha("minha_senha1");
		
		usuarioController.porCodigo(dto);
	}
	
	
	@Test
	public void T9loginErrado() throws IOException, Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@domio.com");
		dto.setSenha("minha_senha1");
		
		InstanciasParaTestes inst = new InstanciasParaTestes();
		try {
			autenticacaoController.login(dto, inst.http, inst.device).getBody();
		}catch (BadCredentialsException e) {
			Assert.assertTrue(true);
			return;
		}
		Assert.assertTrue(false);
	}
	
	@Test
	public void T10login() throws IOException, Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
		
		InstanciasParaTestes inst = new InstanciasParaTestes();

		TokenDTO retorno = (TokenDTO) autenticacaoController.login(dto, inst.http, inst.device).getBody();

		currentToken = retorno.getAccess_token();
	}
	
	@Test
	public void T11Role() throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		currentToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJibHVlcmVmdW5kIiwic3ViIjoiYWRtaW4xQGRvbWluaW8uY29tIiwiYXVkIjoidW5rbm93biIsImlhdCI6MTUyOTg2MTU0OCwiZXhwIjoxNTMyODYxNTQ4fQ.X8AxE0llLKsiDlWPn6UBa5ITqR-yHFHR2njWYfYE4Cg1SDN0V4j2ONSPdywKm4i2Lp02pPELfmPEnJF9t6boNQ";
		httpHeaders.add("Authorization", "Bearer "+ currentToken);
		
		RoleDTO retorno = usuarioController.role();
		retorno.getRole();
		
		ResultActions result = mockMvc.perform(post("/usuario/rolea").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders));
		
	}
	
	
}
