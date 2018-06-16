package br.com.academiadev.bluerefund;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.bluerefund.service.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BluerefundApplicationTests {
	
	
	@Test
	public void enviaEmail() throws MessagingException {
		new EmailService().enviaEmail("mpgabriel95@gmail.com", "minhasenha", "Gabriel");
	}
	
	
	
}
