package br.com.nascimento.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nascimento.dto.PersonDTO;
import br.com.nascimento.model.Person;

@Configuration
public class ModelMapperConfig {

   
    
    @Bean
    public ModelMapper modelMapper() {
    	var modelMapper = new  ModelMapper();
    	modelMapper.createTypeMap(Person.class, PersonDTO.class)
    		.addMapping(Person::getId, PersonDTO::setKey);
    	return modelMapper;
    	
    }

}
