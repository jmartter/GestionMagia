// src/main/java/org/example/gestionmagia/Menu/LoginMenu.java
package org.example.gestionmagia.Menu;

import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.Excepciones.InvalidEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LoginMenu {

    @Autowired
    private LoginService loginService;

    public Usuario displayLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        Usuario loggedInUser = null;

        while (loggedInUser == null) {
            try {
                System.out.println("Ingrese su nombre de usuario:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese su contraseña:");
                String contraseña = scanner.nextLine();
                System.out.println("Ingrese su correo:");
                String correo = scanner.nextLine();

                loggedInUser = loginService.login(nombre, contraseña, correo);

                if (loggedInUser == null) {
                    System.out.println("Nombre de usuario, contraseña o correo incorrectos. Inténtelo de nuevo.");
                } else {
                    System.out.println("Bienvenido, " + loggedInUser.getNombre() + "!");
                    return loggedInUser; // Return the logged-in user
                }
            } catch (InvalidEmailException e) {
                System.out.println(e.getMessage());
                System.out.println("Por favor, ingrese credenciales válidas.");
            }

            System.out.println("1. Intentar de nuevo");
            System.out.println("2. Volver al menú anterior");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (opcion == 2) {
                return null;
            }
        }

        return loggedInUser;
    }
}