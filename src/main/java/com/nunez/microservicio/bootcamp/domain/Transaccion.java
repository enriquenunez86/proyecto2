package com.nunez.microservicio.bootcamp.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Entity
@Document(collection = "transacciones")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // Depósito, Retiro, Transferencia

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String cuentaOrigen;

    private String cuentaDestino; // Solo para transferencias

    private Double saldoDisponible;

    // Constructor para crear una transacción
    public static Transaccion create(String tipo, Double monto, LocalDateTime fecha, String cuentaOrigen, String cuentaDestino, Double saldoDisponible) {
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(tipo);
        transaccion.setMonto(monto);
        transaccion.setFecha(fecha);
        transaccion.setCuentaOrigen(cuentaOrigen);
        transaccion.setCuentaDestino(cuentaDestino);
        transaccion.setSaldoDisponible(saldoDisponible);
        return transaccion;
    }
}