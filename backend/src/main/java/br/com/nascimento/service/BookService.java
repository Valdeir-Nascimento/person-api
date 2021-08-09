package br.com.nascimento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nascimento.exception.ResourceNotFoundException;
import br.com.nascimento.model.Book;
import br.com.nascimento.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public Book salvar(Book book) {
		return bookRepository.save(book);
	}

	public Book findById(Integer idBook) {
		Optional<Book> book = bookRepository.findById(idBook);
		return book.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado registro com Id: " + idBook));
	}

	public void delete(Integer idBook) {
		bookRepository.deleteById(idBook);
	}

}
