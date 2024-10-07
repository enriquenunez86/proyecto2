package com.nunez.microservicio.bootcamp.controller;

import com.nunez.microservicio.bootcamp.domain.Transaccion;
import com.nunez.microservicio.bootcamp.service.TransaccionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionServiceImpl transaccionService;

    @PostMapping("/deposito")
    public ResponseEntity<Object> registrarDeposito(@RequestBody Transaccion transaccion) {
        if (transaccion.getMonto() <= 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        transaccion.setTipo("Depósito");
        Transaccion resultado = transaccionService.registrarTransaccion(transaccion);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @PostMapping("/retiro")
    public ResponseEntity<Object> registrarRetiro(@RequestBody Transaccion transaccion) {
        if (transaccion.getMonto() <= 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        transaccion.setTipo("Retiro");
        Transaccion resultado = transaccionService.registrarTransaccion(transaccion);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Object> registrarTransferencia(@RequestBody Transaccion transaccion) {
        if (transaccion.getMonto() <= 0 || transaccion.getCuentaDestino() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        transaccion.setTipo("Transferencia");
        Transaccion resultado = transaccionService.registrarTransaccion(transaccion);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Transaccion>> consultarHistorial(@RequestParam String cuenta) {
        List<Transaccion> historial = transaccionService.obtenerHistorial(cuenta);
        if (historial.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }
}