package com.nunez.microservicio.bootcamp.dao;

import java.util.Optional;


import com.nunez.microservicio.bootcamp.domain.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository <Cliente, Long> {
	
	public Optional<Cliente> findByDni(String dni);
	
}	
