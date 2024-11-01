package org.example.gestionmagia.Usuario;

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
        if (usuarioService.findByNombre("admin") == null) {
            Usuario admin = new Usuario();
            admin.setNombre("admin");
            admin.setContraseña("admin");
            admin.setCorreo("admin");
            admin.setAdministrador(true);
            usuarioService.save(admin);
        }

        if (usuarioService.findByNombre("usuario") == null) {
            Usuario usuario = new Usuario();
            usuario.setNombre("usuario");
            usuario.setContraseña("usuario");
            usuario.setCorreo("usuario");
            usuario.setAdministrador(false);
            usuarioService.save(usuario);
        }
    }
}