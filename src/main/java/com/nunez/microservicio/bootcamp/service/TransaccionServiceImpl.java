package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.dao.TransaccionRepository;
import com.nunez.microservicio.bootcamp.domain.Transaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransaccionServiceImpl {

    @Autowired
    private TransaccionRepository transaccionRepository;

    public Transaccion registrarTransaccion(Transaccion transaccion) {
        transaccion.setFecha(LocalDateTime.now());
        return transaccionRepository.save(transaccion);
    }

    public List<Transaccion> obtenerHistorial(String cuenta) {
        return transaccionRepository.findByCuentaOrigen(cuenta);
    }

    public boolean validarSaldo(Double saldo, Double monto) {
        return saldo >= monto;
    }

}
