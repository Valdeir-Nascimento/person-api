package br.com.nascimento.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import br.com.nascimento.dto.PersonDTO;
import br.com.nascimento.dto.converter.PersonDTOConverter;
import br.com.nascimento.dto.converter.PersonRequestConverter;
import br.com.nascimento.dto.request.PersonRequest;
import br.com.nascimento.event.RecursoCriadoEvent;
import br.com.nascimento.model.Person;
import br.com.nascimento.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/person/v1")
public class PersonController {

	@Autowired
	private PersonService personService;
	@Autowired
	private PersonDTOConverter personDTOConverter;
	@Autowired
	private PersonRequestConverter personRequestConverter;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<List<PersonDTO>> findAll() {
		List<Person> personList = personService.findAll();
		return ResponseEntity.ok().body(personDTOConverter.to(personList));
	}

	@GetMapping("/{idPerson}")
	public ResponseEntity<PersonDTO> findById(@PathVariable Integer idPerson) {
		PersonDTO personDTO = personDTOConverter.to(personService.findById(idPerson));
		personDTO.add(linkTo(methodOn(PersonController.class).findById(idPerson)).withSelfRel());
		return ResponseEntity.ok().body(personDTO);
	}

	@PostMapping
	public ResponseEntity<PersonDTO> criar(@RequestBody PersonRequest personRequest, HttpServletResponse response) {
		Person person = personRequestConverter.to(personRequest);
		person = personService.salvar(person);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, person.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(personDTOConverter.to(person));
	}

	@PutMapping("/{idPerson}")
	public ResponseEntity<PersonDTO> atualizar(@RequestBody PersonRequest personRequest,
			@PathVariable Integer idPerson) {
		Person personAtual = personService.findById(idPerson);
		personRequestConverter.copyToProperties(personRequest, personAtual);
		personAtual = personService.salvar(personAtual);
		return ResponseEntity.ok().body(personDTOConverter.to(personAtual));
	}

	@DeleteMapping("/{idPerson}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer idPerson) {
		personService.delete(idPerson);
	}

}
