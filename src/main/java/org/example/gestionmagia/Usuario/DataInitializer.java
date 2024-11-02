// src/main/java/org/example/gestionmagia/Usuario/DataInitializer.java
package org.example.gestionmagia.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private UsuarioService usuarioService;

    public void initializeData(){
        if (usuarioService.findByNombre("admin") == null) {
            Usuario admin = new Usuario();
            admin.setNombre("admin");
            admin.setContraseña("admin");
            admin.setCorreo("admin@gmail.com");
            admin.setAdministrador(true);
            usuarioService.save(admin);
        }

        if (usuarioService.findByNombre("usuario") == null) {
            Usuario usuario = new Usuario();
            usuario.setNombre("usuario");
            usuario.setContraseña("usuario");
            usuario.setCorreo("usuario@gmail.com");
            usuario.setAdministrador(false);
            usuarioService.save(usuario);
        }
    }
}