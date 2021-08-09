package br.com.nascimento.dto.converter;

import br.com.nascimento.dto.PersonDTO;
import br.com.nascimento.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public PersonDTO to(Person person) {

        return modelMapper.map(person, PersonDTO.class);
    }

    public List<PersonDTO> to(List<Person> personList) {
        return personList.stream()
                .map(person -> to(person))
                .collect(Collectors.toList());
    }
}
