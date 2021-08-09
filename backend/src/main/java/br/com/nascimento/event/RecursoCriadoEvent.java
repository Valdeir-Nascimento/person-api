package br.com.nascimento.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

@Getter
public class RecursoCriadoEvent extends ApplicationEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
	private Integer id;

	public RecursoCriadoEvent(Object source, HttpServletResponse response, Integer id) {
		super(source);
		this.response = response;
		this.id = id;
	}
}
