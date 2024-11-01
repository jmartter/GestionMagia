// src/main/java/org/example/gestionmagia/Almacenamiento/AlmacenamientoService.java
package org.example.gestionmagia.Almacenamiento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlmacenamientoService {
    @Autowired
    private AlmacenamientoRepository almacenamientoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Almacenamiento> findAll() {
        return almacenamientoRepository.findAll();
    }

    public Almacenamiento save(Almacenamiento almacenamiento) {
        return almacenamientoRepository.save(almacenamiento);
    }

    public void deleteById(Long id) {
        almacenamientoRepository.deleteById(id);
    }

    public Almacenamiento findById(Long id) {
        return almacenamientoRepository.findById(id).orElse(null);
    }

    public void truncateTable() {
        entityManager.createNativeQuery("TRUNCATE TABLE almacenamiento").executeUpdate();
    }
}