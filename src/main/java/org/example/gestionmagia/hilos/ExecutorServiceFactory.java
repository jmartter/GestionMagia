// ExecutorServiceFactory.java
package org.example.gestionmagia.hilos;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ExecutorServiceFactory {

    public ExecutorService createSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
