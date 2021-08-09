package br.com.nascimento.dto.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequest {
	private String author;
	private Date launchDate;
	private Double price;
	private String title;
}
