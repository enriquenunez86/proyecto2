package com.nunez.microservicio.bootcamp.controller;

import com.nunez.microservicio.bootcamp.domain.Cuenta;
import com.nunez.microservicio.bootcamp.domain.Transaccion;
import com.nunez.microservicio.bootcamp.service.CuentaServiceImpl;
import com.nunez.microservicio.bootcamp.service.TransaccionServiceImpl;
import com.nunez.microservicio.bootcamp.service.ValidacionTransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionServiceImpl transaccionService;

    @Autowired
    private CuentaServiceImpl cuentaService;

    @Autowired
    private ValidacionTransaccionService validacionTransaccionService;

    @PostMapping("/deposito")
    public ResponseEntity<Object> registrarDeposito(@RequestBody Transaccion transaccion) {
        // Validar que el monto sea positivo
        if (transaccion.getMonto() <= 0) {
            return new ResponseEntity<>("El monto debe ser mayor a cero", HttpStatus.BAD_REQUEST);
        }

        // Verificar si la cuenta asociada a la transacción existe
        Optional<Cuenta> cuentaOpt = cuentaService.buscarPorNumeroCuenta(transaccion.getCuentaOrigen());
        if (cuentaOpt.isEmpty()) {
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.NOT_FOUND);
        }
        // Obtener la cuenta y actualizar el saldo
        Cuenta cuenta = cuentaOpt.get();
        cuenta.setSaldo(cuenta.getSaldo() + transaccion.getMonto());

        // Guardar la cuenta actualizada
        cuentaService.updateCuenta(cuenta);

        // Registrar la transacción como "Depósito"
        transaccion.setTipo("Depósito");
        Transaccion resultado = transaccionService.registrarTransaccion(transaccion);

        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @PostMapping("/retiro")
    public ResponseEntity<Object> registrarRetiro(@RequestBody Transaccion transaccion) {
        HttpStatus status = validacionTransaccionService.validarRetiro(transaccion);
        if (status != HttpStatus.CREATED) {
            return new ResponseEntity<>(validacionTransaccionService.getMensajeError(), status);
        }

        // Obtener la cuenta
        Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(transaccion.getCuentaOrigen()).get();
        cuenta.setSaldo(cuenta.getSaldo() - transaccion.getMonto());
        cuentaService.updateCuenta(cuenta);

        // Registrar la transacción
        transaccion.setTipo("Retiro");
        Transaccion resultado = transaccionService.registrarTransaccion(transaccion);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Object> registrarTransferencia(@RequestBody Transaccion transaccion) {
        HttpStatus status = validacionTransaccionService.validarTransferencia(transaccion);
        if (status != HttpStatus.CREATED) {
            return new ResponseEntity<>(validacionTransaccionService.getMensajeError(), status);
        }

        // Obtener cuentas
        Cuenta cuentaOrigen = cuentaService.buscarPorNumeroCuenta(transaccion.getCuentaOrigen()).get();
        Cuenta cuentaDestino = cuentaService.buscarPorNumeroCuenta(transaccion.getCuentaDestino()).get();

        // Realizar la transferencia
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - transaccion.getMonto());
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + transaccion.getMonto());
        cuentaService.updateCuenta(cuentaOrigen);
        cuentaService.updateCuenta(cuentaDestino);

        // Registrar la transacción
        transaccion.setTipo("Transferencia");
        Transaccion resultado = transaccionService.registrarTransaccion(transaccion);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Transaccion>> obtenerHistorial() {
        List<Transaccion> historial = transaccionService.obtenerHistorialTransacciones();
        return ResponseEntity.ok(historial);
    }
}
