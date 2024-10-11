package com.nunez.microservicio.bootcamp.dao;

import com.nunez.microservicio.bootcamp.domain.Transaccion;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends MongoRepository<Transaccion, Long> {
    List<Transaccion> findByCuentaOrigen(String cuentaOrigen);
    List<Transaccion> findByCuentaDestino(String cuentaDestino);
}