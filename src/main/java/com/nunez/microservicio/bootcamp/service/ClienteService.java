package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.domain.Cliente;

import java.util.List;
import java.util.Optional;

// Interfaz del servicio
public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByDni(String dni);
    Cliente save(Cliente cliente);
    Cliente update(Long id, Cliente cliente);
    void delete(Long id);
}
