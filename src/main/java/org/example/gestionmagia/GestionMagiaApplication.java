// GestionMagiaApplication.java
package org.example.gestionmagia;

import org.example.gestionmagia.Menu.LoginMenu;
import org.example.gestionmagia.Menu.Menu;
import org.example.gestionmagia.Usuario.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.gestionmagia")
public class GestionMagiaApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GestionMagiaApplication.class, args);
        LoginMenu loginMenu = context.getBean(LoginMenu.class);
        Usuario loggedInUser = loginMenu.displayLoginMenu();
        Menu menu = context.getBean(Menu.class);
        menu.displayMenu(loggedInUser);
    }
}