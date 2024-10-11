package com.nunez.microservicio.bootcamp.controller;

import com.nunez.microservicio.bootcamp.domain.Cuenta;
import com.nunez.microservicio.bootcamp.service.CuentaService;
import com.nunez.microservicio.bootcamp.service.CuentaSaldoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CuentaControllerTest {

    @InjectMocks
    private CuentaController cuentaController;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private CuentaSaldoService cuentaSaldoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCuentaById() {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        when(cuentaService.findById(1L)).thenReturn(Optional.of(cuenta));

        ResponseEntity<Cuenta> response = cuentaController.getCuentaById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuenta, response.getBody());
    }

    @Test
    void testEliminarCuenta() {
        ResponseEntity<Void> response = cuentaController.eliminarCuenta(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDepositarCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(100.0);
        when(cuentaSaldoService.depositar(1L, 50.0)).thenReturn(Optional.of(cuenta));

        ResponseEntity<?> response = cuentaController.depositarCuenta(1L, 50.0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuenta, response.getBody());
    }
}
