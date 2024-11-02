package org.example.gestionmagia.Menu;

import org.example.gestionmagia.excepciones.InvalidEmailException;
import org.example.gestionmagia.Truncate.Borrado;
import org.example.gestionmagia.Hechizos.Hechizo;
import org.example.gestionmagia.Usuario.UsuarioService;
import org.example.gestionmagia.aspecto.Aspecto;
import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.excepciones.PrivilegeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    @Autowired
    private Hechizo hechizo;

    @Autowired
    private Borrado borrado;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Aspecto aspecto;

    @Autowired
    private MainMenu mainMenu;

    public void displayMenu(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Elige un hechizo para lanzar:");
            System.out.println("1. Hechizo 1");
            System.out.println("2. Hechizo 2");
            System.out.println("3. Hechizo 3");
            System.out.println("4. Hechizo 4");
            System.out.println("5. Ataque multiple");
            System.out.println("6. Mostrar información de hilos");
            System.out.println("7. Volver al menú principal");
            System.out.println("8. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcion) {
                    case 1:
                        hechizo.lanzarHechizo1(usuario);
                        break;
                    case 2:
                        hechizo.lanzarHechizo2(usuario);
                        break;
                    case 3:
                        hechizo.lanzarHechizo3(usuario);
                        break;
                    case 4:
                        hechizo.lanzarHechizo4(usuario);
                        break;
                    case 5:
                        System.out.println("Ataque multiple");
                        hechizo.lanzarHechizoMultiple(usuario);
                        break;
                    case 6:
                        System.out.println("Información de los hilos:");
                        for (String info : aspecto.getThreadInfoList()) {
                            System.out.println(info);
                        }
                        break;
                    case 7:
                        mainMenu.displayMainMenu();
                        return;
                    case 8:
                        System.out.println("Saliendo...");
                        hechizo.cerrarExecutor(); // Cierra el ExecutorService al final
                        borrado.truncateUsuarioTable();
                        borrado.truncateAlmacenamientoTable();
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } catch (PrivilegeException e) {
                System.out.println(e.getMessage());
            } catch (InvalidEmailException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}