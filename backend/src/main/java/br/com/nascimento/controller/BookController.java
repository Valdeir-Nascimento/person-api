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

import br.com.nascimento.dto.BookDTO;
import br.com.nascimento.dto.converter.BookDTOConverter;
import br.com.nascimento.dto.converter.BookRequestConverter;
import br.com.nascimento.dto.request.BookRequest;
import br.com.nascimento.event.RecursoCriadoEvent;
import br.com.nascimento.model.Book;
import br.com.nascimento.service.BookService;

@RestController
@RequestMapping(value = "/api/book/v1")
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private BookDTOConverter bookDTOConverter;
	@Autowired
	private BookRequestConverter bookRequestConverter;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<List<BookDTO>> findAll() {
		List<BookDTO> bookList = bookDTOConverter.to(bookService.findAll());
		
		bookList.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getCodigo())).withSelfRel()));
		
		return ResponseEntity.ok().body(bookList);
	}
	
	@GetMapping("/{idBook}")
	public ResponseEntity<BookDTO> findById(@PathVariable Integer idBook) {
		BookDTO bookDTO = bookDTOConverter.to(bookService.findById(idBook));
		bookDTO.add(linkTo(methodOn(PersonController.class).findById(idBook)).withSelfRel());
		return ResponseEntity.ok().body(bookDTO);
	}
	
	@PostMapping
	public ResponseEntity<BookDTO> criar(@RequestBody BookRequest bookRequest, HttpServletResponse response) {
		Book book = bookRequestConverter.to(bookRequest);
		book = bookService.salvar(book);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, book.getCodigo()));
		BookDTO bookDTO = bookDTOConverter.to(book);
		bookDTO.add(linkTo(methodOn(PersonController.class).findById(book.getCodigo())).withSelfRel());
		return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
	}
	
	@PutMapping("/{idBook}")
	public ResponseEntity<BookDTO> atualizar(@RequestBody BookRequest bookRequest,
			@PathVariable Integer idBook) {
		Book bookAtual = bookService.findById(idBook);
		bookRequestConverter.copyToProperties(bookRequest, bookAtual);
		bookAtual = bookService.salvar(bookAtual);
		
		BookDTO bookDTO = bookDTOConverter.to(bookAtual);
		bookDTO.add(linkTo(methodOn(PersonController.class).findById(bookAtual.getCodigo())).withSelfRel());
		
		return ResponseEntity.ok().body(bookDTO);
	}
	
	@DeleteMapping("/{idBook}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer idBook) {
		bookService.delete(idBook);
	}


	
}
