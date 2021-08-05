package br.com.nascimento.dto.converter;

import br.com.nascimento.dto.request.PersonRequest;
import br.com.nascimento.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonRequestConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Person to(PersonRequest personRequest) {
        return modelMapper.map(personRequest, Person.class);
    }

    public void copyToProperties(PersonRequest personRequest, Person person) {
        modelMapper.map(personRequest, person);
    }

}
