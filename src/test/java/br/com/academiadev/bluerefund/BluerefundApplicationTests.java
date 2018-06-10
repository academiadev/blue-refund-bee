package br.com.academiadev.bluerefund;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.bluerefund.exceptions.EmailInvalidoException;
import br.com.academiadev.bluerefund.exceptions.EmailJaCadastradoException;
import br.com.academiadev.bluerefund.exceptions.EmailNaoEncontradoException;
import br.com.academiadev.bluerefund.exceptions.SenhaIncorretaException;
import br.com.academiadev.bluerefund.exceptions.SenhaInvalidaException;
import br.com.academiadev.bluerefund.exceptions.SenhasDiferentesException;
import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.model.Categoria;
import br.com.academiadev.bluerefund.model.Empregado;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.model.Reembolso;
import br.com.academiadev.bluerefund.repository.AdminRepository;
import br.com.academiadev.bluerefund.repository.CategoriaRepository;
import br.com.academiadev.bluerefund.repository.EmpregadoRepository;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;
import br.com.academiadev.bluerefund.repository.ReembolsoRepository;
import br.com.academiadev.bluerefund.service.AdminService;
import br.com.academiadev.bluerefund.service.EmailService;
import br.com.academiadev.bluerefund.service.EmpregadoService;
import br.com.academiadev.bluerefund.service.EmpresaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BluerefundApplicationTests {
	
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private EmpregadoRepository empregadoRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ReembolsoRepository reembolsoRepository;
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private EmpregadoService empregadoService;
	

	@Test
	public void addAdmin() {
		Empresa empresa = new Empresa("empresa");
		empresaRepository.save(empresa);
		adminRepository.save(new Admin("admin1", "admin1@gmail.com", "senha1", empresa));
	}
	
	@Test
	public void addEmpregado() {
		Empresa empresa = new Empresa("empresa2");
		empresaRepository.save(empresa);
		empregadoRepository.save(new Empregado("empregado1", "empregado1@gmail.com", "senha1", empresa));
	}
	
	@Test
	public void addEmpresa() {
		empresaRepository.save(new Empresa("empresa2"));
	}

	@Test
	public void addCategoria() {
		categoriaRepository.save(new Categoria("categoria1"));
	}
	
	@Test
	public void addReembolso() {
		Empresa empresa = new Empresa("empresa2");
		empresaRepository.save(empresa);
		Empregado empregado = new Empregado("empregado1", "empregado1@gmail.com", "senha1", empresa);
		empregadoRepository.save(empregado);
		Categoria categoria = new Categoria("categoria1");
		categoriaRepository.save(categoria);
		reembolsoRepository.save(new Reembolso("reembolso1", categoria , new BigDecimal(123), empregado, LocalDate.now() ));
	}
	
	@Test
	public void addEmpresaService() {
		empresaService.cadastrar("empresa1");
	}
	
	@Test
	public void addAdminService() throws SenhaInvalidaException, EmailInvalidoException, EmailJaCadastradoException {
		adminService.cadastrar("Admin1", "emailDoAdmin1@gmail.com", "asdf_123", "contaazul");
	}
	
	@Test
	public void buscaEmpregadoEmail() {
		Admin admin = adminRepository.findByEmail("admin1@gmail.com");
		System.out.println(admin.getEmail());
		System.out.println(admin.getEmail());
		System.out.println(admin.getEmail());
		System.out.println(admin.getEmail());
		System.out.println(admin.getEmail());

	}
	
	@Test
	public void novaSenha() throws SenhasDiferentesException, EmailNaoEncontradoException, SenhaIncorretaException, SenhaInvalidaException {
		empregadoService.novaSenha("Gaki_988", "Gaki_988", "empregado1@gmail.com");
//		Gaki_988 -1757798382
	}
	
	@Test
	public void enviaEmail() throws MessagingException {
		new EmailService().enviaEmail("mpgabriel95@gmail.com", "minhasenha", "Gabriel");
	}
	
	
	
}
