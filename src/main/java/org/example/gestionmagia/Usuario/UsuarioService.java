package org.example.gestionmagia.Usuario;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Usuario save(Usuario usuario) {
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