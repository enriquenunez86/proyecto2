package com.nunez.microservicio.bootcamp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nunez.microservicio.bootcamp.dao.ClientRepository;
import com.nunez.microservicio.bootcamp.domain.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Cliente> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Cliente> findByDni(String dni) {
        return clientRepository.findByDni(dni);
    }

    @Override
    public Cliente save(Cliente cliente) {
        validateCliente(cliente);
        return clientRepository.save(cliente);
    }

    @Override
    public Cliente update(Long id, Cliente cliente) {
        cliente.setId(id);
        validateCliente(cliente);
        return clientRepository.save(cliente);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private void validateCliente(Cliente cliente) {
        // Lógica adicional de validación si es necesario
        if (cliente.getDni() == null || cliente.getDni().isEmpty()) {
            throw new IllegalArgumentException("DNI no puede ser nulo o vacío");
        }
        // Otras validaciones pueden ir aquí
    }
}