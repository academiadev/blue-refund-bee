package br.com.academiadev.bluerefund.converter;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.academiadev.bluerefund.dto.ReembolsoDTO;
import br.com.academiadev.bluerefund.model.Reembolso;
import br.com.academiadev.bluerefund.repository.CategoriaRepository;
import br.com.academiadev.bluerefund.repository.EmpregadoRepository;

public class ReembolsoConverter implements Converter<Reembolso, ReembolsoDTO> {

	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	EmpregadoRepository empregadoRepository;
	
	@Override
	public ReembolsoDTO toDTO(Reembolso entity) {
		ReembolsoDTO dto = new ReembolsoDTO();
		
		dto.setId((int) (long) entity.getId());
		dto.setNome(entity.getNome());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		dto.setData(entity.getData().format(formatter).toString());
		dto.setCategoria(entity.getCategoria().getNome());
		switch(entity.getStatus()) {
			case AGUARDANDO:
				dto.setStatus("Aguardando");
			break;
			case APROVADO:
				dto.setStatus("Aprovado");
			break;
			case REPROVADO:
				dto.setStatus("Reprovado");
			break;
			default:
			break;
		}
		
		dto.setValorSolicitado(entity.getValorSolicitado().floatValue());
		dto.setValorReembolsado(entity.getValorReembolsado().floatValue());
		dto.setUrlupload(entity.getUrlUpload());
		dto.setEmailEmpregado(entity.getEmpregado().getEmail());
		
		return dto;
	}

	@Override
	public Reembolso toEntity(ReembolsoDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}


//private Integer id;
//private String nome;
//private String data;
//private String categoria;
//private String status;
//private float valorSolicitado;
//private float valorReembolsado;
//private String urlupload;
//private String emailEmpregado;

//private Long id;
//private String nome;
//private LocalDate data;
//private Categoria categoria;
//private StatusReembolso status;
//private BigDecimal valorSolicitado;
//private BigDecimal valorReembolsado;
//private String urlUpload;
//private Empregado empregado;