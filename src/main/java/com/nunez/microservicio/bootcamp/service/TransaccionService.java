package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.domain.Transaccion;

import java.util.List;

// Interfaz para el servicio
public interface TransaccionService {
    Transaccion registrarTransaccion(Transaccion transaccion);
    List<Transaccion> obtenerHistorialTransacciones();
    boolean validarSaldo(Double saldo, Double monto);
}
