package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class TokenDTO {

	@ApiModelProperty(value = "Token do usuário", example = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJibHVlcmVmdW5kIiwic3ViIjoiYWRtaW4xQGRvbWluaW8uY29tIiwiYXVkIjoidW5rbm93biIsImlhdCI6MTUyOTg2MTU0OCwiZXhwIjoxNTMyODYxNTQ4fQ.X8AxE0llLKsiDlWPn6UBa5ITqR-yHFHR2njWYfYE4Cg1SDN0V4j2ONSPdywKm4i2Lp02pPELfmPEnJF9t6boNQ")
	private String access_token;
	@ApiModelProperty(value = "Tempo de expiração do token", example = "3000")
	private Long expires_in;
	
	public TokenDTO() {
		super();
	}

	public TokenDTO(String access_token, Long expires_in) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
	
}