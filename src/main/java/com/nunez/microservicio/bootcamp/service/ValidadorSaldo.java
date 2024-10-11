package com.nunez.microservicio.bootcamp.service;

import org.springframework.stereotype.Service;

// Clase Validador de Saldo, que sigue SRP
@Service
public class ValidadorSaldo {

    public boolean validar(Double saldo, Double monto) {
        return saldo >= monto;
    }
}
