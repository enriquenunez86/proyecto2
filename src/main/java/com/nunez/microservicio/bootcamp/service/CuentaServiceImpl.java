package com.nunez.microservicio.bootcamp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nunez.microservicio.bootcamp.dao.CuentaRepository;
import com.nunez.microservicio.bootcamp.domain.Cuenta;

@Service
public class CuentaServiceImpl {

	@Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

        
    public List<Cuenta> findByClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }
    
    public Cuenta save(Cuenta cuenta) {
        validateCuenta(cuenta);
        return cuentaRepository.save(cuenta);
    }

    public Cuenta update(Long id, Cuenta cuenta) {
    	cuenta.setId(id);
        validateCuenta(cuenta);
        return cuentaRepository.save(cuenta);
    }

    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }

    private void validateCuenta(Cuenta cuenta) {
        // Lógica adicional de validación si es necesario
    }
}
