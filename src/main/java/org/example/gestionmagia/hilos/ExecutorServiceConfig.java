// src/main/java/org/example/gestionmagia/hilos/ExecutorServiceConfig.java
package org.example.gestionmagia.hilos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {

    @Bean(name = "fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(8);  // Crea un pool fijo de 8 hilos
    }

    @Bean(name = "fixedThreadPool2")
    public ExecutorService fixedThreadPool2() {
        return Executors.newFixedThreadPool(8); // Crea un pool fijo de 8 hilos
    }

    @Bean(name = "singleThreadExecutor1")
    public ExecutorService singleThreadExecutor1() {
        return Executors.newSingleThreadExecutor();  // Crea un pool de un solo hilo
    }

    @Bean(name = "singleThreadExecutor2")
    public ExecutorService singleThreadExecutor2() {
        return Executors.newSingleThreadExecutor();  // Crea un pool de un solo hilo
    }

    @Bean(name = "singleThreadExecutor3")
    public ExecutorService singleThreadExecutor3() {
        return Executors.newSingleThreadExecutor();  // Crea un pool de un solo hilo
    }

    @Bean(name = "singleThreadExecutor4")
    public ExecutorService singleThreadExecutor4() {
        return Executors.newSingleThreadExecutor();  // Crea un pool de un solo hilo
    }

    @Bean(name = "customThreadPool")
    public ExecutorService customThreadPool() {
        return Executors.newCachedThreadPool();  // Crea un pool de hilos según demanda
    }

    @Bean(name = "fixedThreadPool3")
    public ExecutorService fixedThreadPool3() {
        return Executors.newFixedThreadPool(2); // Crea un pool fijo de 8 hilos
    }

    @Bean(name = "fixedThreadPool4")
    public ExecutorService fixedThreadPool4() {
        return Executors.newFixedThreadPool(8); // Crea un pool fijo de 8 hilos
    }
}