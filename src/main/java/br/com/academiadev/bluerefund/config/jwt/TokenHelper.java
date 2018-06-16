package br.com.academiadev.bluerefund.config.jwt;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.academiadev.bluerefund.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenHelper extends AbstractTokenHelper {

	
	public String getUsuario(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public LocalDateTime getDataCriacao(String token) {
		LocalDateTime issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = timeProvider.toLocalDateTime(claims.getIssuedAt());
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public LocalDateTime getDataExpiracao(String token) {
		LocalDateTime issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = timeProvider.toLocalDateTime(claims.getExpiration());
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public String getDispositivo(String token) {
		String audience;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			audience = claims.getAudience();
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	public String atualizarToken(String token, Device device) {
		String tokenAtualizado;
		try {
			Claims claims = this.getAllClaimsFromToken(token);
			claims.setIssuedAt(timeProvider.toDate(timeProvider.getDataHoraAtual()));
			tokenAtualizado = Jwts.builder().setClaims(claims).setExpiration(timeProvider.toDate(generateExpirationDate(device))).signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		} catch (Exception e) {
			tokenAtualizado = null;
		}
		return tokenAtualizado;
	}

	public String gerarToken(String username, Device device) {
		String audience = generateAudience(device);
		return Jwts.builder().setIssuer(APP_NAME).setSubject(username).setHeaderParam("email", "docsbruno@gmail.com").setAudience(audience).setIssuedAt(timeProvider.toDate(timeProvider.getDataHoraAtual())).setExpiration(timeProvider.toDate(generateExpirationDate(device)))
				.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
	}

	public int getExpiredIn(Device dispositivo) {
		return dispositivo.isMobile() || dispositivo.isTablet() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
	}

	public Boolean validarToken(String token, UserDetails userDetails) {
		Usuario user = (Usuario) userDetails;
		final String usuario = getUsuario(token);
		final LocalDateTime dataDeCriacao = getDataCriacao(token);
		Boolean foiCriadoAntesDaUltimaTrocaDeSenha = isCreatedBeforeLastPasswordReset(dataDeCriacao, user.getUltimaTrocaDeSenha());
		boolean ehMesmoUsuario = usuario != null && usuario.equals(userDetails.getUsername());
		boolean estaEspirado = getDataExpiracao(token).compareTo(timeProvider.getDataHoraAtual()) <= 0;
		return (ehMesmoUsuario && !foiCriadoAntesDaUltimaTrocaDeSenha && !estaEspirado);
	}

	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}

}