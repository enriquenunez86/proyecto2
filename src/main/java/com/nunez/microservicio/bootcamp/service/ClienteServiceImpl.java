package com.nunez.microservicio.bootcamp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nunez.microservicio.bootcamp.dao.ClientRepository;
import com.nunez.microservicio.bootcamp.domain.Cliente;

@Service
public class ClienteServiceImpl {

    @Autowired
    private ClientRepository clientRepository;

    public List<Cliente> findAll() {
        return clientRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
    
    public Cliente findByDni(String dni) {
        return clientRepository.findByDni(dni).orElse(null);
    }

    public Cliente save(Cliente cliente) {
        validateCliente(cliente);
        return clientRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente cliente) {
        cliente.setId(id);
        validateCliente(cliente);
        return clientRepository.save(cliente);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private void validateCliente(Cliente cliente) {
        // Lógica adicional de validación si es necesario
    }
}
