// Hechizo.java
package org.example.gestionmagia.Hechizos;

import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.hilos.ExecutorServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class Hechizo {

    private final ExecutorService executor;

    @Autowired
    public Hechizo(ExecutorServiceFactory executorServiceFactory) {
        this.executor = executorServiceFactory.createSingleThreadExecutor();
    }

    public void lanzarHechizo1(Usuario usuario) {
        executor.submit(() -> {
            // Hechizo logic
        });
    }

    public void lanzarHechizo2(Usuario usuario) {
        executor.submit(() -> {
            // Hechizo logic
        });
    }

    public void lanzarHechizo3(Usuario usuario) {
        executor.submit(() -> {
            // Hechizo logic
        });
    }

    public void lanzarHechizo4(Usuario usuario) {
        executor.submit(() -> {
            // Hechizo logic
        });
    }

    // Método para cerrar el ExecutorService al final
    public void cerrarExecutor() {
        executor.shutdown();
    }
}