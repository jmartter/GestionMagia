// Hechizo.java
package org.example.gestionmagia.Hechizos;

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

    public void lanzarHechizo1() {
        executor.submit(() -> {
            // Lógica del Hechizo 1
        });
    }

    public void lanzarHechizo2() {
        executor.submit(() -> {
            // Lógica del Hechizo 2
        });
    }

    public void lanzarHechizo3() {
        executor.submit(() -> {
            // Lógica del Hechizo 3
        });
    }

    public void lanzarHechizo4() {
        executor.submit(() -> {
            // Lógica del Hechizo 4
        });
    }

    // Método para cerrar el ExecutorService al final
    public void cerrarExecutor() {
        executor.shutdown();
    }
}
