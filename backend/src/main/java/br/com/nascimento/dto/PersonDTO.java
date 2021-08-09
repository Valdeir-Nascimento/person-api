package br.com.nascimento.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PersonDTO extends ResourceSupport {
	
    private Integer codigo;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
