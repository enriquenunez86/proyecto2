package com.nunez.microservicio.bootcamp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nunez.microservicio.bootcamp.domain.Cliente;
import com.nunez.microservicio.bootcamp.service.ClienteServiceImpl;
import com.nunez.microservicio.bootcamp.service.CuentaServiceImpl;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController<Cuenta> {

    @Autowired
    private ClienteServiceImpl clienteService;
    
    @Autowired
    private CuentaServiceImpl cuentaService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody @Validated Cliente cliente) {

        Cliente clienteBd = clienteService.findByDni(cliente.getDni());
        if(clienteBd != null) {
        	        	
        	return ResponseEntity.ok().body("El cliente con el dni " + cliente.getDni() + " ya existe....");
        }else {
        	
        	Cliente createdCliente = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCliente);	
        }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody @Validated Cliente cliente) {
        Cliente updatedCliente = clienteService.update(id, cliente);
        return updatedCliente != null ? ResponseEntity.ok(updatedCliente) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable("id") Long clienteId){
    	
    	List<com.nunez.microservicio.bootcamp.domain.Cuenta> cuentas = cuentaService.findByClienteId(clienteId);
    	if (cuentas != null && cuentas.size() > 0) {
    		return ResponseEntity.ok().body("No se puede eliminar el cliente porque tiene " + cuentas.size() + " cuenta(s) activa(s)");
    	}
    	Cliente cliente = clienteService.findById(clienteId);
        if(cliente != null) {        	
        	clienteService.delete(clienteId);
        	return ResponseEntity.noContent().build();
        }else {
        	return ResponseEntity.ok().body("El cliente no existe");
        }
    }
    
}
