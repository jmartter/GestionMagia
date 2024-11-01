package org.example.gestionmagia.Usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        createDefaultAdmin();
    }

    public void createDefaultAdmin() {
        if (usuarioRepository.findByCorreo("admin") == null) {
            Usuario admin = new Usuario();
            admin.setNombre("admin");
            admin.setContraseña("admin");
            admin.setCorreo("admin");
            admin.setAdministrador(true);
            usuarioRepository.save(admin);
        }
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Object save(Usuario usuario) {
        // Lógica para guardar el usuario
        // Si la validación falla, se devolverá un ResponseEntity desde el aspecto
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}