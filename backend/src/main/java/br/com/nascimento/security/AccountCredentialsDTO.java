package br.com.nascimento.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountCredentialsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

}
