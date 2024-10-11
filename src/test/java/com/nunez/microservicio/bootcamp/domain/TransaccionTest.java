package com.nunez.microservicio.bootcamp.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransaccionTest {

    @Test
    void testCreateTransaccion() {
        // Datos de prueba
        String tipo = "Depósito";
        Double monto = 1000.0;
        LocalDateTime fecha = LocalDateTime.now();
        String cuentaOrigen = "123456";
        String cuentaDestino = null; // No aplica
        Double saldoDisponible = 5000.0;

        // Crear la transacción usando el método de fábrica
        Transaccion transaccion = Transaccion.create(tipo, monto, fecha, cuentaOrigen, cuentaDestino, saldoDisponible);

        // Verificar que la transacción se ha creado correctamente
        assertEquals(tipo, transaccion.getTipo());
        assertEquals(monto, transaccion.getMonto());
        assertEquals(fecha.toLocalDate(), transaccion.getFecha().toLocalDate()); // Solo comparando la fecha
        assertEquals(cuentaOrigen, transaccion.getCuentaOrigen());
        assertEquals(cuentaDestino, transaccion.getCuentaDestino());
        assertEquals(saldoDisponible, transaccion.getSaldoDisponible());
    }
}