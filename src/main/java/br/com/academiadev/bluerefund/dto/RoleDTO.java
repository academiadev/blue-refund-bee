package br.com.academiadev.bluerefund.dto;

import io.swagger.annotations.ApiModelProperty;

public class RoleDTO {
	
	@ApiModelProperty(value = "Role do usuário", example = "ROLE_ADMIN")
	private String Role;

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}
	
}
