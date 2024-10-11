package com.nunez.microservicio.bootcamp.controller;

import com.nunez.microservicio.bootcamp.domain.Cuenta;
import com.nunez.microservicio.bootcamp.domain.Transaccion;
import com.nunez.microservicio.bootcamp.service.CuentaServiceImpl;
import com.nunez.microservicio.bootcamp.service.TransaccionServiceImpl;
import com.nunez.microservicio.bootcamp.service.ValidacionTransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransaccionControllerTest {

    @InjectMocks
    private TransaccionController transaccionController;

    @Mock
    private TransaccionServiceImpl transaccionService;

    @Mock
    private CuentaServiceImpl cuentaService;

    @Mock
    private ValidacionTransaccionService validacionTransaccionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarDeposito_CuentaExistente_Exitoso() {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(100.0);
        transaccion.setCuentaOrigen("123456");

        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(200.0);

        when(cuentaService.buscarPorNumeroCuenta("123456")).thenReturn(Optional.of(cuenta));
        when(validacionTransaccionService.validarDeposito(transaccion)).thenReturn(HttpStatus.CREATED);
        when(transaccionService.registrarTransaccion(transaccion)).thenReturn(transaccion);

        ResponseEntity<Object> response = transaccionController.registrarDeposito(transaccion);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(cuentaService).updateCuenta(cuenta);
        verify(transaccionService).registrarTransaccion(transaccion);
    }

    @Test
    void registrarDeposito_CuentaNoExistente_NotFound() {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(100.0);
        transaccion.setCuentaOrigen("123456");

        when(cuentaService.buscarPorNumeroCuenta("123456")).thenReturn(Optional.empty());
        when(validacionTransaccionService.validarDeposito(transaccion)).thenReturn(HttpStatus.BAD_REQUEST);

        ResponseEntity<Object> response = transaccionController.registrarDeposito(transaccion);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La cuenta no existe", response.getBody());
    }

    @Test
    void registrarDeposito_MontoNegativo_BadRequest() {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(-100.0);

        when(validacionTransaccionService.validarDeposito(transaccion)).thenReturn(HttpStatus.BAD_REQUEST);

        ResponseEntity<Object> response = transaccionController.registrarDeposito(transaccion);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El monto debe ser mayor a cero", response.getBody());
    }

    // Similar tests for registrarRetiro and registrarTransferencia...

}
