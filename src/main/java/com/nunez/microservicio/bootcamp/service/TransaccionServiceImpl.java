package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.dao.TransaccionRepository;
import com.nunez.microservicio.bootcamp.domain.Transaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Implementación del servicio
@Service
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final ValidadorSaldo validadorSaldo;

    @Autowired
    public TransaccionServiceImpl(TransaccionRepository transaccionRepository, ValidadorSaldo validadorSaldo) {
        this.transaccionRepository = transaccionRepository;
        this.validadorSaldo = validadorSaldo;
    }

    @Override
    public Transaccion registrarTransaccion(Transaccion transaccion) {
        transaccion.setFecha(LocalDateTime.now());
        return transaccionRepository.save(transaccion);
    }

    @Override
    public List<Transaccion> obtenerHistorialTransacciones() {
        return transaccionRepository.findAll();
    }

    @Override
    public boolean validarSaldo(Double saldo, Double monto) {
        return validadorSaldo.validar(saldo, monto);
    }
}

