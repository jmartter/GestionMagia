package org.example.gestionmagia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<org.example.gestionmagia.Usuario, Long> {
}