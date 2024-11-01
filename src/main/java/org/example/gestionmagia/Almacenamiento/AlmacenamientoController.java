// src/main/java/org/example/gestionmagia/Almacenamiento/AlmacenamientoController.java
package org.example.gestionmagia.Almacenamiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/almacenamientos")
public class AlmacenamientoController {
    @Autowired
    private AlmacenamientoRepository almacenamientoRepository;

    @GetMapping
    public List<Almacenamiento> getAllAlmacenamientos() {
        return almacenamientoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Almacenamiento getAlmacenamientoById(@PathVariable Long id) {
        return almacenamientoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Almacenamiento createAlmacenamiento(@RequestBody Almacenamiento almacenamiento) {
        return almacenamientoRepository.save(almacenamiento);
    }

    @DeleteMapping("/{id}")
    public void deleteAlmacenamiento(@PathVariable Long id) {
        almacenamientoRepository.deleteById(id);
    }
}