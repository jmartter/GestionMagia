package org.example.gestionmagia.Hechizos;

import org.example.gestionmagia.Almacenamiento.Almacenamiento;
import org.example.gestionmagia.Almacenamiento.AlmacenamientoRepository;
import org.example.gestionmagia.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;

@Component
public class Hechizo {

    @Autowired
    private AlmacenamientoRepository almacenamientoRepository;

    @Autowired
    @Qualifier("singleThreadExecutor")
    private ExecutorService singleThreadExecutor;

    public void lanzarHechizo1(Usuario usuario) {
        singleThreadExecutor.submit(() -> {
            System.out.println("Pool: singleThreadExecutor, Hilo: " + Thread.currentThread().getName());
            guardarAlmacenamiento(usuario, 1);
        });
    }

    public void lanzarHechizo2(Usuario usuario) {
        singleThreadExecutor.submit(() -> {
            System.out.println("Pool: singleThreadExecutor, Hilo: " + Thread.currentThread().getName());
            guardarAlmacenamiento(usuario, 2);
        });
    }

    public void lanzarHechizo3(Usuario usuario) {
        singleThreadExecutor.submit(() -> {
            System.out.println("Pool: singleThreadExecutor, Hilo: " + Thread.currentThread().getName());
            guardarAlmacenamiento(usuario, 3);
        });
    }

    public void lanzarHechizo4(Usuario usuario) {
        singleThreadExecutor.submit(() -> {
            System.out.println("Pool: singleThreadExecutor, Hilo: " + Thread.currentThread().getName());
            guardarAlmacenamiento(usuario, 4);
        });
    }

    private void guardarAlmacenamiento(Usuario usuario, int metodo) {
        Almacenamiento almacenamiento = new Almacenamiento();
        almacenamiento.setUsuario(usuario);
        almacenamiento.setMetodo(metodo);
        almacenamiento.setTimestamp(LocalDateTime.now());
        almacenamientoRepository.save(almacenamiento);
    }

    public void cerrarExecutor() {
        singleThreadExecutor.shutdown();
    }
}