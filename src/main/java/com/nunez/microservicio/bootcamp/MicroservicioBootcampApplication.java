package com.nunez.microservicio.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.nunez.microservicio.bootcamp.dao")
public class MicroservicioBootcampApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioBootcampApplication.class, args);
	}

}
