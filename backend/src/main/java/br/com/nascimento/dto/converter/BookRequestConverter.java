package br.com.nascimento.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nascimento.dto.request.BookRequest;
import br.com.nascimento.model.Book;

@Component
public class BookRequestConverter {

	@Autowired
	private ModelMapper modelMapper;

	public Book to(BookRequest bookRequest) {
		return modelMapper.map(bookRequest, Book.class);
	}

	public void copyToProperties(BookRequest bookRequest, Book book) {
		modelMapper.map(bookRequest, book);
	}

}
