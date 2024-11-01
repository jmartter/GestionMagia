// src/main/java/org/example/gestionmagia/Hechizos/Hechizo.java
package org.example.gestionmagia.Hechizos;

import org.example.gestionmagia.Almacenamiento.Almacenamiento;
import org.example.gestionmagia.Almacenamiento.AlmacenamientoRepository;
import org.example.gestionmagia.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Hechizo {

    @Autowired
    private AlmacenamientoRepository almacenamientoRepository;

    public void lanzarHechizo1(Usuario usuario) {
        // Lógica del hechizo 1
        guardarAlmacenamiento(usuario, 1);
    }

    public void lanzarHechizo2(Usuario usuario) {
        // Lógica del hechizo 2
        guardarAlmacenamiento(usuario, 2);
    }

    public void lanzarHechizo3(Usuario usuario) {
        // Lógica del hechizo 3
        guardarAlmacenamiento(usuario, 3);
    }

    public void lanzarHechizo4(Usuario usuario) {
        // Lógica del hechizo 4
        guardarAlmacenamiento(usuario, 4);
    }

    private void guardarAlmacenamiento(Usuario usuario, int metodo) {
        Almacenamiento almacenamiento = new Almacenamiento();
        almacenamiento.setUsuario(usuario);
        almacenamiento.setMetodo(metodo);
        almacenamiento.setTimestamp(LocalDateTime.now());
        almacenamientoRepository.save(almacenamiento);
    }

    public void cerrarExecutor() {
        // Lógica para cerrar el ExecutorService si es necesario
    }
}