package org.example.gestionmagia.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario) {
        Object result = usuarioService.save(usuario);
        if (result instanceof ResponseEntity) {
            return (ResponseEntity<?>) result;
        }
        return ResponseEntity.ok(result);
    }
}