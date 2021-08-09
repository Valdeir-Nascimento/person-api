package br.com.nascimento.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer codigo;
    
    @Column(nullable = false, length = 180)
    private String author;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date launchDate;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false, length = 250)
    private String title;
    
    
    
}
