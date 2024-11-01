package org.example.gestionmagia;

import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
                usuarioService.save(usuario);
                System.out.println("User registered successfully");
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
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
        scanner.close();
    }
}