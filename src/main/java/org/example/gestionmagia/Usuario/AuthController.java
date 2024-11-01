package org.example.gestionmagia.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(@RequestParam String correo, @RequestParam String contraseña) {
        Usuario usuario = usuarioService.findByCorreo(correo);
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario) {
        if (usuarioService.findByCorreo(usuario.getCorreo()) != null) {
            return "User already exists";
        }
        usuarioService.save(usuario);
        return "User registered successfully";
    }
}