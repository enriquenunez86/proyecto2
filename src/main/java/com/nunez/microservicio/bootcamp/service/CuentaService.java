package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.domain.Cuenta;

import java.util.List;
import java.util.Optional;

// Interfaz del servicio
public interface CuentaService {
    List<Cuenta> findAll();
    Optional<Cuenta> findById(Long id);
    Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta);
    Cuenta updateCuenta(Cuenta cuenta);
    List<Cuenta> findByClienteId(Long clienteId);
    Cuenta save(Cuenta cuenta);
    Cuenta update(Long id, Cuenta cuenta);
    void delete(Long id);
}
