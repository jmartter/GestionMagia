// src/main/java/org/example/gestionmagia/Almacenamiento/AlmacenamientoService.java
package org.example.gestionmagia.Almacenamiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Transactional
    public void truncateTable() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE almacenamiento").executeUpdate();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}