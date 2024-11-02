package org.example.gestionmagia.Menu;

import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioService usuarioService;

    public Usuario login(String nombre, String contraseña, String correo) {
        return usuarioService.findAll().stream()
                .filter(usuario -> usuario.getNombre().equals(nombre) &&
                        usuario.getContraseña().equals(contraseña) &&
                        usuario.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }
}