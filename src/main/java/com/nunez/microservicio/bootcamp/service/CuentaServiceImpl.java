package com.nunez.microservicio.bootcamp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nunez.microservicio.bootcamp.dao.CuentaRepository;
import com.nunez.microservicio.bootcamp.domain.Cuenta;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    public Optional<Cuenta> findById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    @Override
    public Cuenta updateCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public List<Cuenta> findByClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }

    @Override
    public Cuenta save(Cuenta cuenta) {
        validateCuenta(cuenta);
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta update(Long id, Cuenta cuenta) {
        cuenta.setId(id);
        validateCuenta(cuenta);
        return cuentaRepository.save(cuenta);
    }

    @Override
    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }

    private void validateCuenta(Cuenta cuenta) {
        // Lógica de validación si es necesaria
        if (cuenta.getNumeroCuenta() == null || cuenta.getNumeroCuenta().isEmpty()) {
            throw new IllegalArgumentException("El número de cuenta no puede ser nulo o vacío");
        }
        // Otras validaciones pueden ir aquí
    }
}