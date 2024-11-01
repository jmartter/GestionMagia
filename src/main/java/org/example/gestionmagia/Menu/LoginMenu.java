// src/main/java/org/example/gestionmagia/Menu/LoginMenu.java
package org.example.gestionmagia.Menu;


import org.example.gestionmagia.Usuario.Usuario;
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
            System.out.println("Ingrese su nombre de usuario:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese su contraseña:");
            String contraseña = scanner.nextLine();

            loggedInUser = loginService.login(nombre, contraseña);

            if (loggedInUser == null) {
                System.out.println("Nombre de usuario o contraseña incorrectos. Inténtelo de nuevo.");
            } else {
                System.out.println("Bienvenido, " + loggedInUser.getNombre() + "!");
            }
        }

        return loggedInUser;
    }
}