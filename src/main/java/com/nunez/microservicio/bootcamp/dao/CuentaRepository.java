package com.nunez.microservicio.bootcamp.dao;

import java.util.List;

import com.nunez.microservicio.bootcamp.domain.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends MongoRepository <Cuenta, Long>{

    public List<Cuenta> findByClienteId (Long clienteId);
}
