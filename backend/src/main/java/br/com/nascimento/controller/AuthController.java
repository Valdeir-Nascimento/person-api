package br.com.nascimento.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nascimento.exception.jwt.JwtTokenProvider;
import br.com.nascimento.repository.UserRepository;
import br.com.nascimento.security.AccountCredentialsDTO;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsDTO credentialsDTO) {
		try {
			var username = credentialsDTO.getUsername();
			var password = credentialsDTO.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			var user = userRepository.findByUserName(username);
			var token = "";
			if (user != null) {
				token = tokenProvider.createToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			
			Map<Object, Object> model = new HashMap<>(); 
			
			model.put("username", username);
			model.put("token", token);
			
			return ResponseEntity.ok().body(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
	
	
}
