package br.com.academiadev.bluerefund;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import br.com.academiadev.bluerefund.Util.InstanciasParaTestes;
import br.com.academiadev.bluerefund.controller.AutenticacaoController;
import br.com.academiadev.bluerefund.controller.UsuarioController;
import br.com.academiadev.bluerefund.dto.AprovaReembolsoDTO;
import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.dto.CadastroPorCodigoDTO;
import br.com.academiadev.bluerefund.dto.CadastroReembolsoDTO;
import br.com.academiadev.bluerefund.dto.CategoriaDTO;
import br.com.academiadev.bluerefund.dto.LoginDTO;
import br.com.academiadev.bluerefund.dto.TokenDTO;
import br.com.academiadev.bluerefund.exceptions.CodigosInconsistentesException;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmpresaNaoEncontradaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.model.Categoria;
import br.com.academiadev.bluerefund.model.Reembolso;
import br.com.academiadev.bluerefund.model.Usuario;
import br.com.academiadev.bluerefund.repository.CategoriaRepository;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;
import br.com.academiadev.bluerefund.repository.ReembolsoRepository;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;

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
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ReembolsoRepository reembolsoRepository;
	
	@Autowired
	private AutenticacaoController autenticacaoController;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	String currentToken;
	
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
	
	
	@Test
	public void A1cadastraAdminEEmpresaEmailInvalido() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
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
	public void A2cadastraAdminEEmpresaSenhaInvalida() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
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
	public void A3cadastraAdminEEmpresa() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		CadastroAdminDTO dto = new CadastroAdminDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setNome("Admin1");
		dto.setEmpresa("Empresa1");
		dto.setSenha("minha_senha1");
		usuarioController.adminEEmpresa(dto);
		
		Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());
		Assert.assertTrue(usuario!=null);
	}
	
	@Test
	public void A4cadastraAdminEEmpresaEmailJaCadastrado() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
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
	public void A5cadastraPorCodigoEmpresaNaoEncontrada() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
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
	public void A6cadastraPorCodigoSenhaInvalida() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
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
	public void A7cadastraPorCodigoEmailJaCadastrado() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
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
	public void A8cadastraPorCodigo() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setEmpresa(empresaRepository.findByNome("Empresa1").getCodigo());
		dto.setNome("empregado1");
		dto.setSenha("minha_senha1");
		
		usuarioController.porCodigo(dto);
	}
	
	@Test
	public void A8cadastraPorCodigo2() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
		dto.setEmail("empregado2@dominio.com");
		dto.setEmpresa(empresaRepository.findByNome("Empresa1").getCodigo());
		dto.setNome("empregado1");
		dto.setSenha("minha_senha1");
		
		usuarioController.porCodigo(dto);
	}
	
	
	@Test
	public void A9loginErrado() throws IOException, Exception {
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
	public void B0login() throws IOException, Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
		
		InstanciasParaTestes inst = new InstanciasParaTestes();

		TokenDTO retorno = (TokenDTO) autenticacaoController.login(dto, inst.http, inst.device).getBody();

		currentToken = retorno.getAccess_token();
	}
	
	@Test
	public void B1RoleAdmin() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);

		result = mockMvc.perform(get("/usuario/role").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		jsonObj = new JSONObject(content);
		
		Assert.assertTrue(jsonObj.getString("role").equals("ROLE_ADMIN"));
	}
	
	@Test
	public void B2RoleUser() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);

		result = mockMvc.perform(get("/usuario/role").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		jsonObj = new JSONObject(content);
		
		Assert.assertTrue(jsonObj.getString("role").equals("ROLE_USER"));
	}

	@Test
	public void B3Isauth() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);

		result = mockMvc.perform(post("/auth/isauth").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		jsonObj = new JSONObject(content);	
		
		Assert.assertTrue(jsonObj.getString("isAuth").equals("true"));
		}
	
	@Test
	public void B3RefreshToken() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);

		ResultActions resultAction = mockMvc.perform(post("/auth/refresh").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders));
		resultAction.andExpect(status().isOk());
	}
	
	@Test
	public void B4AdicionaCategoria() throws Exception {
		CategoriaDTO dto = new CategoriaDTO();
		dto.setNome("Alimentação");
		
		ResultActions resultAction = mockMvc.perform(post("/categoria/adiciona").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto)));
		resultAction.andExpect(status().isOk());
		
		Categoria categoria =  categoriaRepository.findByNome("Alimentação");
		assertNotNull(categoria);
	}
	
	@Test
	public void B5AdicionaCategoriaRepetida() throws Exception {
		CategoriaDTO dto = new CategoriaDTO();
		dto.setNome("Alimentação");
		
		ResultActions resultAction = mockMvc.perform(post("/categoria/adiciona").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto)));
		resultAction.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void B6AdicionaReembolsoEmpregado1() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		CadastroReembolsoDTO dto2 = new CadastroReembolsoDTO();
		dto2.setCategoria("Alimentação");
		dto2.setData("1995-11-10");
		dto2.setNome("Almoço Garten");
		dto2.setUploadUrl("server\\imagem.jpg");
		dto2.setValorSolicitado((float) 25.5);
		
		ResultActions resultAction = mockMvc.perform(post("/reembolso/adiciona").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders).content(convertObjectToJsonBytes(dto2)));
		resultAction.andExpect(status().isOk());
		
		ArrayList<Reembolso> reembolsos = (ArrayList<Reembolso>) reembolsoRepository.findAll();
		assertTrue(reembolsos.size() == 1);
	}
	
	@Test
	public void B7AdicionaReembolsoCategoriaInvalida() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		CadastroReembolsoDTO dto2 = new CadastroReembolsoDTO();
		dto2.setCategoria("Alitação");
		dto2.setData("1995-11-10");
		dto2.setNome("Almoço Garten");
		dto2.setUploadUrl("server\\imagem.jpg");
		dto2.setValorSolicitado((float) 25.5);
		
		ResultActions resultAction = mockMvc.perform(post("/reembolso/adiciona").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders).content(convertObjectToJsonBytes(dto2)));
		resultAction.andExpect(status().is4xxClientError());
		
		ArrayList<Reembolso> reembolsos = (ArrayList<Reembolso>) reembolsoRepository.findAll();
		assertTrue(reembolsos.size() == 1);
	}
	
	@Test
	public void B8AdicionaReembolsoEmpregado2() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado2@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		CadastroReembolsoDTO dto2 = new CadastroReembolsoDTO();
		dto2.setCategoria("Alimentação");
		dto2.setData("2014-09-05");
		dto2.setNome("Janta na cantina");
		dto2.setUploadUrl("server\\imagem2.jpg");
		dto2.setValorSolicitado((float) 15.0);
		
		ResultActions resultAction = mockMvc.perform(post("/reembolso/adiciona").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders).content(convertObjectToJsonBytes(dto2)));
		resultAction.andExpect(status().isOk());
		
		ArrayList<Reembolso> reembolsos = (ArrayList<Reembolso>) reembolsoRepository.findAll();
		assertTrue(reembolsos.size() == 2);
	}
	
	@Test
	public void B9BuscaReembolsosEmpregado() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado2@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		ResultActions resultAction = mockMvc.perform(get("/reembolso/buscaPorEmpregado").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders));
		resultAction.andExpect(status().isOk());
		
	}
	
	@Test
	public void C0BuscaReembolsosEmpregadoSemRole() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		ResultActions resultAction = mockMvc.perform(get("/reembolso/buscaPorEmpregado").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders));
		resultAction.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void C0BuscaReembolsosAdmin() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		ResultActions resultAction = mockMvc.perform(get("/reembolso/buscaPorEmpresa").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders));
		resultAction.andExpect(status().isOk());
		
	}
	
	@Test
	public void C1BuscaReembolsosAdminSemRole() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		ResultActions resultAction = mockMvc.perform(get("/reembolso/buscaPorEmpresa").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders));
		resultAction.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void C2AprovaReembolso() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		result = mockMvc.perform(get("/reembolso/buscaPorEmpresa").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		JSONArray jsonarray = new JSONArray(content);
		jsonObj= (JSONObject) jsonarray.get(0);
		
		AprovaReembolsoDTO dtoAprova = new AprovaReembolsoDTO();
		dtoAprova.setId(Integer.parseInt(jsonObj.getString("id")));
		dtoAprova.setValorReembolsado(Float.parseFloat(jsonObj.getString("valorReembolsado")));
		
		ResultActions resultAction = mockMvc.perform(post("/reembolso/aprova").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dtoAprova)).headers(httpHeaders));
		resultAction.andExpect(status().isOk());		
	}
	
	@Test
	public void C3ReprovaReembolso() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		result = mockMvc.perform(get("/reembolso/buscaPorEmpresa").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		JSONArray jsonarray = new JSONArray(content);
		jsonObj= (JSONObject) jsonarray.get(1);
		
		ResultActions resultAction = mockMvc.perform(post("/reembolso/reprova").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(Integer.parseInt(jsonObj.getString("id")))).headers(httpHeaders));
		resultAction.andExpect(status().isOk());		
	}
	
	@Test
	public void C4ReprovaReembolsoSemROle() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		result = mockMvc.perform(get("/reembolso/buscaPorEmpregado").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		JSONArray jsonarray = new JSONArray(content);
		jsonObj= (JSONObject) jsonarray.get(0);
		
		ResultActions resultAction = mockMvc.perform(post("/reembolso/reprova").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(Integer.parseInt(jsonObj.getString("id")))).headers(httpHeaders));
		resultAction.andExpect(status().is4xxClientError());		
	}
	
	@Test
	public void C5AprovaReembolsoSemRole() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		result = mockMvc.perform(get("/reembolso/buscaPorEmpregado").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		JSONArray jsonarray = new JSONArray(content);
		jsonObj= (JSONObject) jsonarray.get(0);
		
		AprovaReembolsoDTO dtoAprova = new AprovaReembolsoDTO();
		dtoAprova.setId(Integer.parseInt(jsonObj.getString("id")));
		dtoAprova.setValorReembolsado(Float.parseFloat(jsonObj.getString("valorReembolsado")));
		
		ResultActions resultAction = mockMvc.perform(post("/reembolso/aprova").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dtoAprova)).headers(httpHeaders));
		resultAction.andExpect(status().is4xxClientError());		
	}
	
	@Test
	public void C6ExcluiReembolsoSemRole() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		result = mockMvc.perform(get("/reembolso/buscaPorEmpresa").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		JSONArray jsonarray = new JSONArray(content);
		jsonObj= (JSONObject) jsonarray.get(0);
		
		ResultActions resultAction = mockMvc.perform(post("/reembolso/exclui").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(Integer.parseInt(jsonObj.getString("id")))).headers(httpHeaders));
		resultAction.andExpect(status().is4xxClientError());		
	}
	
	@Test
	public void C7ExcluiReembolso() throws Exception {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("empregado1@dominio.com");
		dto.setSenha("minha_senha1");
		
		MvcResult result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(dto))).andReturn();
		String content = result.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		this.currentToken = jsonObj.getString("access_token");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + currentToken);
		
		result = mockMvc.perform(get("/reembolso/buscaPorEmpregado").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
		content = result.getResponse().getContentAsString();
		JSONArray jsonarray = new JSONArray(content);
		jsonObj= (JSONObject) jsonarray.get(0);
		
		ResultActions resultAction = mockMvc.perform(delete("/reembolso/exclui").contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(Integer.parseInt(jsonObj.getString("id")))).headers(httpHeaders));
		resultAction.andExpect(status().isOk());		
	}
	
	
	
	
	

	
}
