package org.example.gestionmagia.Hechizos;

import org.example.gestionmagia.Almacenamiento.Almacenamiento;
import org.example.gestionmagia.Almacenamiento.AlmacenamientoRepository;
import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.aspecto.Aspecto;
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
    @Qualifier("singleThreadExecutor1")
    private ExecutorService singleThreadExecutor1;

    @Autowired
    @Qualifier("singleThreadExecutor2")
    private ExecutorService singleThreadExecutor2;

    @Autowired
    @Qualifier("singleThreadExecutor3")
    private ExecutorService singleThreadExecutor3;

    @Autowired
    @Qualifier("singleThreadExecutor4")
    private ExecutorService singleThreadExecutor4;
    @Autowired
    @Qualifier("fixedThreadPool3")
    private ExecutorService fixedThreadPool3;

    @Autowired
    private Aspecto aspecto;

public void lanzarHechizo1(Usuario usuario) {
    singleThreadExecutor1.submit(() -> {
        aspecto.captureThreadInfo("singleThreadExecutor1");
        guardarAlmacenamiento(usuario, 1);
    });
}

public void lanzarHechizo2(Usuario usuario) {
    singleThreadExecutor2.submit(() -> {
        aspecto.captureThreadInfo("singleThreadExecutor2");
        guardarAlmacenamiento(usuario, 2);
    });
}

public void lanzarHechizo3(Usuario usuario) {
    singleThreadExecutor3.submit(() -> {
        aspecto.captureThreadInfo("singleThreadExecutor3");
        guardarAlmacenamiento(usuario, 3);
    });
}

public void lanzarHechizo4(Usuario usuario) {
    singleThreadExecutor4.submit(() -> {
        aspecto.captureThreadInfo("singleThreadExecutor4");
        guardarAlmacenamiento(usuario, 4);
    });
}

public void lanzarHechizoMultiple(Usuario usuario) {
    fixedThreadPool3.submit(() -> {
        try {
            Thread.sleep(1000);
            aspecto.captureThreadInfo("fixedThreadPool3");
            lanzarHechizo1(usuario);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    });
    fixedThreadPool3.submit(() -> {
        try {
            Thread.sleep(1000);
            aspecto.captureThreadInfo("fixedThreadPool3");
            lanzarHechizo2(usuario);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    });
    fixedThreadPool3.submit(() -> {
        try {
            Thread.sleep(1000);
            aspecto.captureThreadInfo("fixedThreadPool3");
            lanzarHechizo3(usuario);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
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
        singleThreadExecutor1.shutdown();
        singleThreadExecutor2.shutdown();
        singleThreadExecutor3.shutdown();
        singleThreadExecutor4.shutdown();
    }
}