package br.com.nascimento.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
