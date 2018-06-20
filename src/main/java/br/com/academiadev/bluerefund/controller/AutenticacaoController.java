package br.com.academiadev.bluerefund.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.bluerefund.common.DeviceProvider;
import br.com.academiadev.bluerefund.config.jwt.TokenHelper;
import br.com.academiadev.bluerefund.dto.LoginDTO;
import br.com.academiadev.bluerefund.dto.TokenDTO;
import br.com.academiadev.bluerefund.model.Usuario;
import br.com.academiadev.bluerefund.service.CustomUserDetailsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutenticacaoController {

	@Autowired
	private TokenHelper tokenHelper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private DeviceProvider deviceProvider;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody LoginDTO authenticationRequest, HttpServletResponse response, Device dispositivo) throws AuthenticationException, IOException {
 		authenticationRequest.setEmail("mpgabriel95@hotmail.com");
		final Authentication autenticacao = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(autenticacao);
		Usuario usuario = (Usuario) autenticacao.getPrincipal();
		String token = tokenHelper.gerarToken(usuario.getEmail(), dispositivo);
		int expiresIn = tokenHelper.getExpiredIn(dispositivo);
		return ResponseEntity.ok(new TokenDTO(token, Long.valueOf(expiresIn)));
	}

	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		String token = tokenHelper.getToken(request);
		Device dispositivo = deviceProvider.getDispositivo(request);
		if (token != null && principal != null) {
			String tokenAtualizado = tokenHelper.atualizarToken(token, dispositivo);
			int expiracao = tokenHelper.getExpiredIn(dispositivo);
			return ResponseEntity.ok(new TokenDTO(tokenAtualizado, Long.valueOf(expiracao)));
		} else {
			TokenDTO tokenDTO = new TokenDTO();
			return ResponseEntity.accepted().body(tokenDTO);
		}
	}

	@ApiImplicitParams({ //
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@RequestMapping(value = "/isauth", method = RequestMethod.POST)
	public ResponseEntity<?> isAuth(HttpServletRequest request) {
		String token = tokenHelper.getToken(request);

		if (token != null) {
			String usuarioDoToken = tokenHelper.getUsuario(token);
			if (usuarioDoToken != null) {
				UserDetails usuario = userDetailsService.loadUserByUsername(usuarioDoToken);
				if (tokenHelper.validarToken(token, usuario)) {
					Map<String, String> result = new HashMap<>();
					result.put("isAuth", "true");
					return ResponseEntity.ok().body(result);
				}
			}
		}

		Map<String, String> result = new HashMap<>();
		result.put("isAuth", "false");
		return ResponseEntity.ok().body(result);
	}


}