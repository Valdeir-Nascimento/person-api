package br.com.nascimento.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nascimento.dto.BookDTO;
import br.com.nascimento.model.Book;

@Component
public class BookDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public BookDTO to(Book book) {

        return modelMapper.map(book, BookDTO.class);
    }

    public List<BookDTO> to(List<Book> bookList) {
        return bookList.stream()
                .map(book -> to(book))
                .collect(Collectors.toList());
    }
}
