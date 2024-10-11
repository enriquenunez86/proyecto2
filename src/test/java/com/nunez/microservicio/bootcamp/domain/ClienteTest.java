package com.nunez.microservicio.bootcamp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import jakarta.validation.ConstraintViolation;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Validator validator;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        cliente = new Cliente();
    }

    @Test
    public void testClienteValido() {
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setDni("12345678");
        cliente.setEmail("juan.perez@example.com");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNombreObligatorio() {
        cliente.setApellido("Pérez");
        cliente.setDni("12345678");
        cliente.setEmail("juan.perez@example.com");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        assertFalse(violations.isEmpty());

        ConstraintViolation<Cliente> violation = violations.iterator().next();
        assertEquals("El nombre es obligatorio", violation.getMessage());
    }

    @Test
    public void testEmailFormatoInvalido() {
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setDni("12345678");
        cliente.setEmail("email_invalido");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        assertFalse(violations.isEmpty());

        ConstraintViolation<Cliente> violation = violations.iterator().next();
        assertEquals("El email debe tener un formato válido", violation.getMessage());
    }
}