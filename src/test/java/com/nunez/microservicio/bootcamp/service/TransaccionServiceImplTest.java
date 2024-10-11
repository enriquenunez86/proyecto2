package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.dao.TransaccionRepository;
import com.nunez.microservicio.bootcamp.domain.Transaccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransaccionServiceImplTest {

    @InjectMocks
    private TransaccionServiceImpl transaccionService;

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private ValidadorSaldo validadorSaldo;

    private Transaccion transaccion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transaccion = new Transaccion();
        transaccion.setId(1L);
        transaccion.setTipo("Depósito");
        transaccion.setMonto(100.0);
        transaccion.setFecha(LocalDateTime.now());
    }

    @Test
    void testRegistrarTransaccion() {
        when(transaccionRepository.save(transaccion)).thenReturn(transaccion);

        Transaccion result = transaccionService.registrarTransaccion(transaccion);
        assertNotNull(result);
        assertEquals(transaccion, result);
        verify(transaccionRepository, times(1)).save(transaccion);
    }

    @Test
    void testObtenerHistorialTransacciones() {
        when(transaccionRepository.findAll()).thenReturn(Arrays.asList(transaccion));

        List<Transaccion> result = transaccionService.obtenerHistorialTransacciones();
        assertEquals(1, result.size());
        assertEquals(transaccion, result.get(0));
    }

    @Test
    void testValidarSaldo() {
        when(validadorSaldo.validar(100.0, 50.0)).thenReturn(true);

        boolean result = transaccionService.validarSaldo(100.0, 50.0);
        assertTrue(result);
    }

    @Test
    void testValidarSaldoInsuficiente() {
        when(validadorSaldo.validar(50.0, 100.0)).thenReturn(false);

        boolean result = transaccionService.validarSaldo(50.0, 100.0);
        assertFalse(result);
    }
}