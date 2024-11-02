// src/main/java/org/example/gestionmagia/Menu/MainMenu.java
package org.example.gestionmagia.Menu;

import org.example.gestionmagia.Usuario.DataInitializer;
import org.example.gestionmagia.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainMenu {

    @Autowired
    private LoginMenu loginMenu;

    @Autowired
    private RegisterMenu registerMenu;

    @Autowired
    private Menu menu;

    @Autowired
    private DataInitializer dataInitializer;

    public void displayMainMenu() {
        try {
            dataInitializer.initializeData(); // Initialize data
        } catch (Exception e) {
            System.out.println("Error initializing data: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Elige una opción:");
            System.out.println("1. Logearte");
            System.out.println("2. Registrarte");
            System.out.println("3. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcion) {
                case 1:
                    Usuario usuario = loginMenu.displayLoginMenu();
                    if (usuario != null) {
                        menu.displayMenu(usuario);
                    }
                    break;
                case 2:
                    registerMenu.displayRegisterMenu();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }
}