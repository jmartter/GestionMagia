// src/main/java/org/example/gestionmagia/Menu/MainMenu.java
package org.example.gestionmagia.Menu;

import org.example.gestionmagia.Truncate.Borrado;
import org.example.gestionmagia.Usuario.DataInitializer;
import org.example.gestionmagia.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class MainMenu {

    @Autowired
    private LoginMenu loginMenu;
    @Autowired
    private Borrado borrado;

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

            try {
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
                        borrado.truncateUsuarioTable();
                        borrado.truncateAlmacenamientoTable();
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}