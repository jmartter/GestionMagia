package org.example.gestionmagia;

import org.example.gestionmagia.Hechizos.Hechizo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    @Autowired
    private Hechizo hechizo;

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Elige un hechizo para lanzar:");
            System.out.println("1. Hechizo 1");
            System.out.println("2. Hechizo 2");
            System.out.println("3. Hechizo 3");
            System.out.println("4. Hechizo 4");
            System.out.println("5. Salir");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    hechizo.lanzarHechizo1();
                    break;
                case 2:
                    hechizo.lanzarHechizo2();
                    break;
                case 3:
                    hechizo.lanzarHechizo3();
                    break;
                case 4:
                    hechizo.lanzarHechizo4();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }
}