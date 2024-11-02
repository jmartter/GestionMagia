// src/main/java/org/example/gestionmagia/GestionMagiaApplication.java
package org.example.gestionmagia;

import org.example.gestionmagia.Menu.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionMagiaApplication implements CommandLineRunner {

    @Autowired
    private MainMenu mainMenu;

    public static void main(String[] args) {
        SpringApplication.run(GestionMagiaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mainMenu.displayMainMenu();
    }
}