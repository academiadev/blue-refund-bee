package br.com.academiadev.bluerefund.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.AdminRepository;

public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	public void cadastrar(String nome, String email, String senha, String nomeEmpresa){
		//validar email
		//Buscar se não tem um admin com o mesmo email
		//Buscar se não tem um empregado co o mesmo email
		//Buscar se já existe esta empresa, se não existe, cadastra-la
		//Verificar se a senha tem 8 caracteres com caracteres especiais e numeros
		
		Empresa empresa = new Empresa(nomeEmpresa);
		Admin admin = new Admin(nome, email, senha, empresa);
		
		adminRepository.save(admin);
	}
	
	public boolean validarEmail(String email)
    {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

}
