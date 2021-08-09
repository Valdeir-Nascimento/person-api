package br.com.nascimento.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String author;
	private Date launchDate;
	private Double price;
	private String title;

}
