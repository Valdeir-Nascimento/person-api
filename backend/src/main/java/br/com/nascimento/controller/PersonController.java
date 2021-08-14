package br.com.nascimento.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.nascimento.dto.PersonDTO;
import br.com.nascimento.dto.converter.PersonDTOConverter;
import br.com.nascimento.dto.converter.PersonRequestConverter;
import br.com.nascimento.dto.request.PersonRequest;
import br.com.nascimento.event.RecursoCriadoEvent;
import br.com.nascimento.model.Person;
import br.com.nascimento.service.PersonService;
import io.swagger.annotations.Api;

@Api(value = "PersonEndpoint", description = "Description for person", tags = {"Person Endpoint"})
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
	
	//@ApiOperation(value = "Find all people recorded")
	@GetMapping
	public ResponseEntity<List<PersonDTO>> findAll() {
		List<PersonDTO> personList = personDTOConverter.to(personService.findAll());
		
		personList.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		return ResponseEntity.ok().body(personList);
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
		PersonDTO personDTO = personDTOConverter.to(person);
		personDTO.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
		return ResponseEntity.status(HttpStatus.CREATED).body(personDTO);
	}

	@PutMapping("/{idPerson}")
	public ResponseEntity<PersonDTO> atualizar(@RequestBody PersonRequest personRequest,
			@PathVariable Integer idPerson) {
		Person personAtual = personService.findById(idPerson);
		personRequestConverter.copyToProperties(personRequest, personAtual);
		personAtual = personService.salvar(personAtual);
		
		PersonDTO personDTO = personDTOConverter.to(personAtual);
		personDTO.add(linkTo(methodOn(PersonController.class).findById(personAtual.getId())).withSelfRel());
		
		return ResponseEntity.ok().body(personDTO);
	}

	@DeleteMapping("/{idPerson}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer idPerson) {
		personService.delete(idPerson);
	}

}
