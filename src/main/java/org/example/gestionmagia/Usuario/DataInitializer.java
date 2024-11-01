// src/main/java/org/example/gestionmagia/DataInitializer.java
package org.example.gestionmagia;

import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        Usuario admin = new Usuario();
        admin.setNombre("admin");
        admin.setContraseña("admin");
        admin.setCorreo("admin");
        admin.setAdministrador(true);

        Usuario usuario = new Usuario();
        usuario.setNombre("usuario");
        usuario.setContraseña("usuario");
        usuario.setCorreo("usuario");
        usuario.setAdministrador(false);

        usuarioService.save(admin);
        usuarioService.save(usuario);
    }
}