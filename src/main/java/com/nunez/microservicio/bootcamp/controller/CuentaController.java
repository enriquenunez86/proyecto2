package com.nunez.microservicio.bootcamp.controller;

import java.util.List;
import java.util.Optional;

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

import com.nunez.microservicio.bootcamp.dao.CuentaRepository;
import com.nunez.microservicio.bootcamp.domain.Cuenta;
import com.nunez.microservicio.bootcamp.service.CuentaServiceImpl;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaServiceImpl cuentaService;
    
    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.findById(id);
        return cuenta != null ? ResponseEntity.ok(cuenta) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody @Validated Cuenta cuenta) {

        Cuenta createdCuenta = cuentaService.save(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCuenta);
    }

    @PutMapping("/{id}/depositar/{monto}")
    public ResponseEntity<?> depositarCuenta(@PathVariable Long id, @PathVariable Double monto) {
        //Cuenta updatedCuenta = cuentaService.update(id, monto);
    	//return updatedCuenta != null ? ResponseEntity.ok(updatedCuenta) : ResponseEntity.notFound().build();

    	Optional<Cuenta> oCuenta = cuentaRepository.findById(id);
    	if(oCuenta.isPresent()) {
    		Cuenta cuenta = oCuenta.get();
    		double nuevoSaldo = cuenta.getSaldo() + monto;
    		cuenta.setSaldo(nuevoSaldo);
    		cuentaRepository.save(cuenta);
    		
    		return ResponseEntity.ok().body(cuenta);
    		
    	} else {
    		
    		return ResponseEntity.ok().body("Cuenta no existe");
    	}
    	
    	
    }
    
    @PutMapping("/{id}/retirar/{monto}")
    public ResponseEntity<?> retirarCuenta(@PathVariable Long id, @PathVariable Double monto) {

    	Optional<Cuenta> oCuenta = cuentaRepository.findById(id);
    	if(oCuenta.isPresent()) {
    		Cuenta cuenta = oCuenta.get();
    		
    		if(cuenta.getSaldo()>=monto) {
    			double nuevoSaldo = cuenta.getSaldo() - monto;
        		cuenta.setSaldo(nuevoSaldo);
        		cuentaRepository.save(cuenta);
        		
        		return ResponseEntity.ok().body(cuenta);
    			
    		} else {
    			return ResponseEntity.ok("Saldo insuficiente...");
    		}
    		
    		
    	} else {
    		
    		return ResponseEntity.ok("Cuenta no existe");
    	}
    	
    	
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

