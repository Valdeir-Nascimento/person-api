package br.com.nascimento.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
