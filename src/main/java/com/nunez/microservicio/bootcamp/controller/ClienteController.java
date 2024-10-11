package com.nunez.microservicio.bootcamp.controller;

import java.util.List;

import com.nunez.microservicio.bootcamp.domain.Cliente;
import com.nunez.microservicio.bootcamp.domain.Cuenta;
import com.nunez.microservicio.bootcamp.service.ClienteService;
import com.nunez.microservicio.bootcamp.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final CuentaService cuentaService;

    @Autowired
    public ClienteController(ClienteService clienteService, CuentaService cuentaService) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createCliente(@RequestBody @Validated Cliente cliente) {
        if (clienteService.findByDni(cliente.getDni()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El cliente con el DNI " + cliente.getDni() + " ya existe.");
        }
        Cliente createdCliente = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado con ID: " + createdCliente.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody @Validated Cliente cliente) {
        cliente.setId(id);
        return ResponseEntity.ok(clienteService.update(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        List<Cuenta> cuentas = cuentaService.findByClienteId(id);
        if (!cuentas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar el cliente porque tiene cuentas activas.");
        }
        clienteService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
