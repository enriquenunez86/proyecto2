package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.dao.ClientRepository;
import com.nunez.microservicio.bootcamp.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClientRepository clientRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setDni("12345678");
        // Configura otros atributos de Cliente según sea necesario
    }

    @Test
    void testFindAll() {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(cliente));

        List<Cliente> result = clienteService.findAll();
        assertEquals(1, result.size());
        assertEquals(cliente, result.get(0));
    }

    @Test
    void testFindById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> result = clienteService.findById(1L);
        assertNotNull(result);
        assertEquals(cliente, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Cliente> result = clienteService.findById(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSave() {
        when(clientRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.save(cliente);
        assertNotNull(result);
        assertEquals(cliente, result);
        verify(clientRepository, times(1)).save(cliente);
    }

    @Test
    void testUpdate() {
        when(clientRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.update(1L, cliente);
        assertNotNull(result);
        assertEquals(cliente, result);
        verify(clientRepository, times(1)).save(cliente);
    }

    @Test
    void testDelete() {
        clienteService.delete(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testValidateClienteWithNullDni() {
        cliente.setDni(null);
        assertThrows(IllegalArgumentException.class, () -> clienteService.save(cliente));
    }

    @Test
    void testValidateClienteWithEmptyDni() {
        cliente.setDni("");
        assertThrows(IllegalArgumentException.class, () -> clienteService.save(cliente));
    }
}