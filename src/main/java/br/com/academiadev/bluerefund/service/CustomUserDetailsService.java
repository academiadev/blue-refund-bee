package br.com.academiadev.bluerefund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.academiadev.bluerefund.model.Admin;
import br.com.academiadev.bluerefund.repository.AdminRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Admin user = adminRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
		} else {
			return (UserDetails) user;
		}
	}

	public void trocarSenha(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		log.debug("Re-authenticating user '" + username + "' for password change request.");
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));

		log.debug("Changing password for user '" + username + "'");
		Admin user = (Admin) loadUserByUsername(username);

		user.setHashSenha(passwordEncoder.encode(newPassword).hashCode());
		adminRepository.save(user);

	}
}
