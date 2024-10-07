package com.nunez.microservicio.bootcamp.domain;

import com.nunez.microservicio.bootcamp.util.TipoCuenta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(collection = "cuentas")
@Data
public class Cuenta {	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCuenta;

    private Double saldo;

    private TipoCuenta tipoCuenta;
    
    private Long clienteId;

    // Getters y setters
    // ...
}