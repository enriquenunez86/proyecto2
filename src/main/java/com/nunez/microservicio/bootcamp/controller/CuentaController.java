package com.nunez.microservicio.bootcamp.controller;

import com.nunez.microservicio.bootcamp.domain.Cuenta;
import com.nunez.microservicio.bootcamp.service.CuentaService;
import com.nunez.microservicio.bootcamp.service.CuentaSaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;
    private final CuentaSaldoService cuentaSaldoService;

    @Autowired
    public CuentaController(CuentaService cuentaService, CuentaSaldoService cuentaSaldoService) {
        this.cuentaService = cuentaService;
        this.cuentaSaldoService = cuentaSaldoService;
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        List<Cuenta> cuentas = cuentaService.findAll();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        return cuentaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody @Validated Cuenta cuenta) {
        Cuenta createdCuenta = cuentaService.save(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCuenta);
    }

    @PutMapping("/{id}/depositar/{monto}")
    public ResponseEntity<Object> depositarCuenta(@PathVariable Long id, @PathVariable Double monto) {
        Optional<Cuenta> optionalCuenta = cuentaSaldoService.depositar(id, monto);

        if (optionalCuenta.isPresent()) {
            return ResponseEntity.ok(optionalCuenta.get()); // Retorna Cuenta si se encuentra
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada"); // Retorna el mensaje de error
        }
    }

    @PutMapping("/{id}/retirar/{monto}")
    public ResponseEntity<Object> retirarCuenta(@PathVariable Long id, @PathVariable Double monto) {
        Optional<Cuenta> optionalCuenta = cuentaSaldoService.retirar(id, monto);

        if (optionalCuenta.isPresent()) {
            return ResponseEntity.ok(optionalCuenta.get()); // Retorna Cuenta si se encuentra
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada o saldo insuficiente"); // Retorna el mensaje de error
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
