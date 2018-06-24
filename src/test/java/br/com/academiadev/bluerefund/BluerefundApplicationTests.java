package br.com.academiadev.bluerefund;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.bluerefund.controller.UsuarioController;
import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.dto.LoginDTO;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BluerefundApplicationTests {
	
	@Autowired
	private UsuarioController usuarioController;
	

//	@Test
//	public void apagaTudo() {
//		categoriaRepository.deleteAll();
//		reembolsoRepository.deleteAll();
//		empresaRepository.deleteAll();
//		usuarioRepository.deleteAll();
//		
//		Autorizacao aut1 = new Autorizacao();
//		aut1.setNome("ROLE_USER");
//		autorizacaoRepository.save(aut1);
//		
//		Autorizacao aut2 = new Autorizacao();
//		aut1.setNome("ROLE_ADMIN");
//		autorizacaoRepository.save(aut2);
//		
//	}
//	
//	@Test
//	public void cadastraAdminEEmpresaEmailInvalido() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
//		CadastroAdminDTO dto = new CadastroAdminDTO();
//		dto.setEmail("emailErrado");
//		dto.setNome("Admin1");
//		dto.setNomeEmpresa("Empresa1");
//		dto.setSenha("senha_admin1");
//		
//		try {
//			usuarioController.adminEEmpresa(dto);
//		} catch (EmailInvalidoException e) {
//			Assert.assertTrue(true);
//			return;
//		}
//		
//		Assert.assertTrue(false);
//	}
//	
//	@Test
//	public void cadastraAdminEEmpresaSenhaInvalida() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
//		CadastroAdminDTO dto = new CadastroAdminDTO();
//		dto.setEmail("admin1@dominio.com");
//		dto.setNome("Admin1");
//		dto.setNomeEmpresa("Empresa1");
//		dto.setSenha("senhaErrada");
//		
//		try {
//			usuarioController.adminEEmpresa(dto);
//		} catch (SenhaInvalidaException e) {
//			Assert.assertTrue(true);
//			return;
//		}
//		
//		Assert.assertTrue(false);
//	}
//	
//	@Test
//	public void cadastraAdminEEmpresa() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
//		CadastroAdminDTO dto = new CadastroAdminDTO();
//		dto.setEmail("admin1@dominio.com");
//		dto.setNome("Admin1");
//		dto.setNomeEmpresa("Empresa1");
//		dto.setSenha("minha_senha1");
//		usuarioController.adminEEmpresa(dto);
//	}
//	
//	@Test
//	public void cadastraAdminEEmpresaEmailJaCadastrado() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
//		CadastroAdminDTO dto = new CadastroAdminDTO();
//		dto.setEmail("admin1@dominio.com");
//		dto.setNome("Admin1");
//		dto.setNomeEmpresa("Empresa1");
//		dto.setSenha("minha_senha1");
//		
//		try {
//			usuarioController.adminEEmpresa(dto);
//		} catch (EmailJaCadastradoException e) {
//			Assert.assertTrue(true);
//			return;
//		}
//		
//		Assert.assertTrue(false);
//	}
//	
//	@Test
//	public void cadastraPorCodigoEmpresaNaoEncontrada() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
//		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
//		dto.setEmail("empregado1@dominio.com");
//		dto.setCodigoEmpresa(0);
//		dto.setNome("empregado1");
//		dto.setSenha("minha_senha1");
//		
//		try {
//			usuarioController.porCodigo(dto);
//		} catch (EmpresaNaoEncontradaException e) {
//			Assert.assertTrue(true);
//			return;
//		}
//		
//		Assert.assertTrue(false);
//	}
//	
//	@Test
//	public void cadastraPorCodigoSenhaInvalida() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
//		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
//		dto.setEmail("empregado1@dominio.com");
//		dto.setCodigoEmpresa(empresaRepository.findByNome("Empresa1").getCodigo());
//		dto.setNome("empregado1");
//		dto.setSenha("senhaErrada");
//		
//		try {
//			usuarioController.porCodigo(dto);
//		} catch (SenhaInvalidaException e) {
//			Assert.assertTrue(true);
//			return;
//		}
//		
//		Assert.assertTrue(false);
//	}
//	
//	@Test
//	public void cadastraPorCodigoEmailJaCadastrado() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
//		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
//		dto.setEmail("admin1@dominio.com");
//		dto.setCodigoEmpresa(empresaRepository.findByNome("Empresa1").getCodigo());
//		dto.setNome("empregado1");
//		dto.setSenha("minha_senha1");
//		
//		try {
//			usuarioController.porCodigo(dto);
//		} catch (EmailJaCadastradoException e) {
//			Assert.assertTrue(true);
//			return;
//		}
//		
//		Assert.assertTrue(false);
//	}
//	
//	@Test
//	public void cadastraPorCodigo() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException, CodigosInconsistentesException, EmpresaNaoEncontradaException {
//		CadastroPorCodigoDTO dto = new CadastroPorCodigoDTO();
//		dto.setEmail("empregado1@dominio.com");
//		dto.setCodigoEmpresa(empresaRepository.findByNome("Empresa1").getCodigo());
//		dto.setNome("empregado1");
//		dto.setSenha("minha_senha1");
//		
//		usuarioController.porCodigo(dto);
//	}
	
	
	@Test
	public void login() {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("admin1@dominio.com");
		dto.setSenha("minha_senha1");
	}
	
	
}
