package org.example.gestionmagia.Hechizos;

import org.example.gestionmagia.hilos.ExecutorServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class Hechizo {

    @Autowired
    private ExecutorServiceFactory executorServiceFactory;

    public void lanzarHechizo1() {
        ExecutorService executor = executorServiceFactory.createSingleThreadExecutor();
        executor.submit(() -> {
            // Lógica del hechizo 1
            System.out.println("Lanzando hechizo 1");
            System.out.println("Pool: SingleThreadExecutor, Hilo: " + Thread.currentThread().getName());
        });
        executor.shutdown();
    }

    public void lanzarHechizo2() {
        ExecutorService executor = executorServiceFactory.createSingleThreadExecutor();
        executor.submit(() -> {
            // Lógica del hechizo 2
            System.out.println("Lanzando hechizo 2");
            System.out.println("Pool: SingleThreadExecutor, Hilo: " + Thread.currentThread().getName());
        });
        executor.shutdown();
    }

    public void lanzarHechizo3() {
        ExecutorService executor = executorServiceFactory.createSingleThreadExecutor();
        executor.submit(() -> {
            // Lógica del hechizo 3
            System.out.println("Lanzando hechizo 3");
            System.out.println("Pool: SingleThreadExecutor, Hilo: " + Thread.currentThread().getName());
        });
        executor.shutdown();
    }

    public void lanzarHechizo4() {
        ExecutorService executor = executorServiceFactory.createSingleThreadExecutor();
        executor.submit(() -> {
            // Lógica del hechizo 4
            System.out.println("Lanzando hechizo 4");
            System.out.println("Pool: SingleThreadExecutor, Hilo: " + Thread.currentThread().getName());
        });
        executor.shutdown();
    }
}