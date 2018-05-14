package br.com.academiadev.bluerefund;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.AdminRepository;
import br.com.academiadev.bluerefund.service.AdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BluerefundApplicationTests {
	
	@Autowired
	private AdminRepository adminRepository;

	@Test
	public void addAdmin() {
//		adminRepository.save(new Admin("asdasd", "asdasd", "asdasd", new Empresa(1l, "asdasd")));
		System.out.println("asd");
	}

}
