package com.nunez.microservicio.bootcamp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Entity
@Document(collection = "clientes")
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank(message = "El nombre es obligatorio")
	private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
	private String apellido;

	@NotBlank(message = "El DNI es obligatorio")
	@Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe tener 8 dígitos")
	private String dni;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
	private String email;

	public boolean isPresent() {
        return false;
    }

	// Getters y setters
	// ...
}