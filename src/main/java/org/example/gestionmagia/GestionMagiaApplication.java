package org.example.gestionmagia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.gestionmagia")
public class GestionMagiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionMagiaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            // Application started
            System.out.println("Application started successfully.");
        };
    }
}