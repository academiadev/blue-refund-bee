package br.com.academiadev.bluerefund;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	

	@Test
	public void addAdmin() {
		adminRepository.save(new Admin("admin1", "admin1@gmail.com", "senha1", new Empresa("asdasd")));
	}
	
	@Test
	public void addEmpregado() {
		empregadoRepository.save(new Empregado("empregado1", "empregado1@gmail.com", "senha1", new Empresa("asdasd")));
	}
	
	@Test
	public void addEmpresa() {
		empresaRepository.save(new Empresa("empresa1"));
	}

	@Test
	public void addCategoria() {
		categoriaRepository.save(new Categoria("categoria1"));
	}
	
	@Test
	public void addReembolso() {
		reembolsoRepository.save(new Reembolso("reembolso1", new Categoria(), new BigDecimal(123), new Empregado()));
	}
	
}