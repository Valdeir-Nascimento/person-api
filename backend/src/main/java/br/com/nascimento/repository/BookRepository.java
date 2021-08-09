package br.com.nascimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nascimento.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
