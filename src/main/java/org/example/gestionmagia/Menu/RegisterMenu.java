package org.example.gestionmagia.Menu;

import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.Usuario.UsuarioService;
import org.example.gestionmagia.excepciones.InvalidEmailException;
import org.example.gestionmagia.excepciones.InvalidNameException;
import org.example.gestionmagia.excepciones.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class RegisterMenu {

    @Autowired
    private UsuarioService usuarioService;

    public void displayRegisterMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Elige una opción:");
            System.out.println("1. Crear usuario");
            System.out.println("2. Volver al menú anterior");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese el nombre de usuario: ");
                        String nombre = scanner.nextLine();
                        System.out.println();

                        System.out.print("Ingrese la contraseña: ");
                        String contraseña = scanner.nextLine();
                        System.out.println();

                        System.out.print("Ingrese el correo: ");
                        String correo = scanner.nextLine();
                        System.out.println();

                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setNombre(nombre);
                        nuevoUsuario.setContraseña(contraseña);
                        nuevoUsuario.setCorreo(correo);
                        nuevoUsuario.setAdministrador(false);

                        usuarioService.save(nuevoUsuario);
                        System.out.println("Usuario creado exitosamente.");
                    } catch (InvalidEmailException | InvalidNameException | InvalidPasswordException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }
}