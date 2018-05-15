package br.com.academiadev.bluerefund.service;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.bluerefund.model.Empresa;
import br.com.academiadev.bluerefund.repository.EmpresaRepository;

@Primary
@Service
public class EmpresaService implements EmpresaInterface {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public void cadastrar(String nome) {
		Empresa empresa = new Empresa(nome);
		
		verificaCodigo(empresa);
		
		this.empresaRepository.save(empresa);
	}

	private void verificaCodigo(Empresa empresa) {
		while(BuscaCodigo(empresa.getCodigo())) {
			empresa.setNovoCodigo();
		}
	}
	
	private boolean BuscaCodigo(Integer codigo) {
		
		List<Empresa> listaEmpresas = empresaRepository.findAll();
		for (Empresa empresa2 : listaEmpresas) {
			if(empresa2.getCodigo() == codigo ) {
				return true;
			}
		}
		return false;
		
	}

}
