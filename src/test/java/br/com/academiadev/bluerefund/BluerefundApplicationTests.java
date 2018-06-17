package br.com.academiadev.bluerefund;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.bluerefund.controller.UsuarioController;
import br.com.academiadev.bluerefund.dto.CadastroAdminDTO;
import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.repository.CategoriaRepository;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;
import br.com.academiadev.bluerefund.repository.ReembolsoRepository;
import br.com.academiadev.bluerefund.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BluerefundApplicationTests {
	
	//Repositorys
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private ReembolsoRepository reembolsoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//Controllers
	@Autowired
	private UsuarioController usuarioController;
	
	@Test
	public void apagaTudo() {
		categoriaRepository.deleteAll();
		empresaRepository.deleteAll();
		reembolsoRepository.deleteAll();
		usuarioRepository.deleteAll();
	}
	
	@Test
	public void cadastraAdminEEmpresaEmailInvalido() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		CadastroAdminDTO dto = new CadastroAdminDTO();
		dto.setEmail("emailErrado");
		dto.setNome("Admin1");
		dto.setNomeEmpresa("Empresa1");
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
	public void cadastraAdminEEmpresaSenhaInvalida() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		CadastroAdminDTO dto = new CadastroAdminDTO();
		dto.setEmail("email@dominio.com");
		dto.setNome("Admin1");
		dto.setNomeEmpresa("Empresa1");
		dto.setSenha("senhaErrada");
		
		try {
			usuarioController.adminEEmpresa(dto);
		} catch (SenhaInvalidaException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.assertTrue(false);
	}
	
	
	
}
