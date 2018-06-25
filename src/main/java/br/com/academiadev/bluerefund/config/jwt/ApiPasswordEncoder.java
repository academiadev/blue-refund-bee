package br.com.academiadev.bluerefund.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApiPasswordEncoder implements PasswordEncoder {

	private PasswordEncoder passwordEncoder;

	@Value("${jwt.pass_secret}")
	private String PASS_SECRET;

	public ApiPasswordEncoder() {
		super();
	}

	public ApiPasswordEncoder(@Lazy BCryptPasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return passwordEncoder.encode(PASS_SECRET + rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(PASS_SECRET + rawPassword, encodedPassword);
	}

}
