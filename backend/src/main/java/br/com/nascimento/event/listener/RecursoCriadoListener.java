package br.com.nascimento.event.listener;

import br.com.nascimento.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {
    @Override
    public void onApplicationEvent(RecursoCriadoEvent event) {
        HttpServletResponse response = event.getResponse();
        Integer id = event.getId();
        adicionarHeaderLocation(response, id);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Integer id) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
