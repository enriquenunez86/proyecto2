package com.nunez.microservicio.bootcamp.service;

import com.nunez.microservicio.bootcamp.dao.CuentaRepository;
import com.nunez.microservicio.bootcamp.domain.Cuenta;
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

public class CuentaServiceImplTest {

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @Mock
    private CuentaRepository cuentaRepository;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setNumeroCuenta("1234567890");
        // Configura otros atributos de Cuenta según sea necesario
    }

    @Test
    void testFindAll() {
        when(cuentaRepository.findAll()).thenReturn(Arrays.asList(cuenta));

        List<Cuenta> result = cuentaService.findAll();
        assertEquals(1, result.size());
        assertEquals(cuenta, result.get(0));
    }

    @Test
    void testFindById() {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        Optional<Cuenta> result = cuentaService.findById(1L);
        assertNotNull(result);
        assertEquals(cuenta, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        when(cuentaRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Cuenta> result = cuentaService.findById(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testBuscarPorNumeroCuenta() {
        when(cuentaRepository.findByNumeroCuenta("1234567890")).thenReturn(Optional.of(cuenta));

        Optional<Cuenta> result = cuentaService.buscarPorNumeroCuenta("1234567890");
        assertTrue(result.isPresent());
        assertEquals(cuenta, result.get());
    }

    @Test
    void testSave() {
        when(cuentaRepository.save(cuenta)).thenReturn(cuenta);

        Cuenta result = cuentaService.save(cuenta);
        assertNotNull(result);
        assertEquals(cuenta, result);
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void testUpdate() {
        when(cuentaRepository.save(cuenta)).thenReturn(cuenta);

        Cuenta result = cuentaService.update(1L, cuenta);
        assertNotNull(result);
        assertEquals(cuenta, result);
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void testDelete() {
        cuentaService.delete(1L);
        verify(cuentaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testValidateCuentaWithNullNumeroCuenta() {
        cuenta.setNumeroCuenta(null);
        assertThrows(IllegalArgumentException.class, () -> cuentaService.save(cuenta));
    }

    @Test
    void testValidateCuentaWithEmptyNumeroCuenta() {
        cuenta.setNumeroCuenta("");
        assertThrows(IllegalArgumentException.class, () -> cuentaService.save(cuenta));
    }
}