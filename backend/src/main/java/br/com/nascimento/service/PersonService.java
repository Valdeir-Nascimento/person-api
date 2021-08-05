package br.com.nascimento.service;

import br.com.nascimento.exception.ResourceNotFoundException;
import br.com.nascimento.model.Person;
import br.com.nascimento.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person salvar(Person person) {
        return personRepository.save(person);
    }

    public Person findById(Integer idPerson) {
        Optional<Person> person = personRepository.findById(idPerson);
        return person.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado registro com Id: " + idPerson));
    }

    public void delete(Integer idPerson) {
        personRepository.deleteById(idPerson);
    }


}
