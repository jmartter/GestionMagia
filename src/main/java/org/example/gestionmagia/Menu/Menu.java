package org.example.gestionmagia.Menu;

import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.Usuario.UsuarioService;
import org.example.gestionmagia.Respuesta.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    @Autowired
    private UsuarioService usuarioService;

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Delete User");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (option == 1) {
                System.out.print("Enter email: ");
                String correo = scanner.nextLine();
                System.out.print("Enter password: ");
                String contraseña = scanner.nextLine();
                String result = usuarioService.findByCorreo(correo) != null && usuarioService.findByCorreo(correo).getContraseña().equals(contraseña) ? "Login successful" : "Invalid credentials";
                System.out.println(result);
            } else if (option == 2) {
                boolean registered = false;
                while (!registered) {
                    System.out.print("Enter name: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String correo = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String contraseña = scanner.nextLine();
                    Usuario usuario = new Usuario();
                    usuario.setNombre(nombre);
                    usuario.setCorreo(correo);
                    usuario.setContraseña(contraseña);
                    Object result = usuarioService.save(usuario);
                    if (result instanceof ResponseEntity) {
                        ResponseEntity<?> response = (ResponseEntity<?>) result;
                        if (response.getBody() instanceof CustomErrorType) {
                            CustomErrorType error = (CustomErrorType) response.getBody();
                            System.out.println("Error: " + error.getMessage());
                        }
                    } else {
                        System.out.println("User registered successfully");
                        registered = true;
                    }
                }
            } else if (option == 3) {
                System.out.print("Enter email of the user to delete: ");
                String correo = scanner.nextLine();
                Usuario usuario = usuarioService.findByCorreo(correo);
                if (usuario != null) {
                    usuarioService.deleteById(usuario.getId());
                    System.out.println("User deleted successfully");
                } else {
                    System.out.println("User not found");
                }
            } else if (option == 4) {
                System.exit(0);
            } else {
                System.out.println("Invalid option");
            }
        }
    }
}