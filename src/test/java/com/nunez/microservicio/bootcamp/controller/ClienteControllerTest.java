package com.nunez.microservicio.bootcamp.controller;

import com.nunez.microservicio.bootcamp.domain.Cliente;
import com.nunez.microservicio.bootcamp.domain.Cuenta;
import com.nunez.microservicio.bootcamp.service.ClienteService;
import com.nunez.microservicio.bootcamp.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaService cuentaService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setDni("12345678");
        cliente.setEmail("juan.perez@example.com");
    }

    @Test
    void getAllClientes_ReturnsListOfClientes() {
        when(clienteService.findAll()).thenReturn(Arrays.asList(cliente));

        ResponseEntity<List<Cliente>> response = clienteController.getAllClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getClienteById_ReturnsCliente() {
        when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

        ResponseEntity<Cliente> response = clienteController.getClienteById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void createCliente_ReturnsCreatedCliente() {
        when(clienteService.findByDni(cliente.getDni())).thenReturn(Optional.empty());
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<String> response = clienteController.createCliente(cliente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Cliente creado con ID:"));
    }

    @Test
    void updateCliente_ReturnsUpdatedCliente() {
        when(clienteService.update(any(Long.class), any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.updateCliente(1L, cliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void testDeleteClienteSinCuentasActivas() throws Exception {
        Long clienteId = 1L;

        // Simula que el cliente no tiene cuentas activas
        when(cuentaService.findByClienteId(clienteId)).thenReturn(Collections.emptyList());

        // Realiza la llamada al controlador
        ResponseEntity<String> response = clienteController.deleteCliente(clienteId);

        // Verifica que el estado sea 204 NO CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verifica que se haya llamado a eliminar el cliente
        verify(clienteService, times(1)).delete(clienteId);
    }

    @Test
    void deleteCliente_WithActiveAccounts_ReturnsConflict() {
        when(cuentaService.findByClienteId(1L)).thenReturn(Arrays.asList(new Cuenta()));

        ResponseEntity<String> response = clienteController.deleteCliente(1L);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().contains("No se puede eliminar el cliente porque tiene cuentas activas."));
    }
}
