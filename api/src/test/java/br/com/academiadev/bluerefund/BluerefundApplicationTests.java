package br.com.academiadev.bluerefund;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.model.Empregado;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.AdminRepository;
import br.com.academiadev.bluerefund.repository.EmpregadoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BluerefundApplicationTests {
	
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private EmpregadoRepository empregadoRepository;

	@Test
	public void addAdmin() {
		adminRepository.save(new Admin("admin1", "admin1@gmail.com", "senha1", new Empresa("asdasd")));
		System.out.println("sdasd");
	}
	
	@Test
	public void addEmpregado() {
		empregadoRepository.save(new Empregado("empregado1", "empregado1@gmail.com", "senha1", new Empresa("asdasd")));
		System.out.println("sdasd");
	}

}
