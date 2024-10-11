package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.dao.CuentaRepository;
import com.nunez.microservicio.bootcamp.domain.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuentaSaldoService {

    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaSaldoService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Optional<Cuenta> depositar(Long id, Double monto) {
        return cuentaRepository.findById(id).map(cuenta -> {
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            cuentaRepository.save(cuenta);
            return cuenta;
        });
    }

    public Optional<Cuenta> retirar(Long id, Double monto) {
        return cuentaRepository.findById(id).filter(cuenta -> cuenta.getSaldo() >= monto).map(cuenta -> {
            cuenta.setSaldo(cuenta.getSaldo() - monto);
            cuentaRepository.save(cuenta);
            return cuenta;
        });
    }
}
