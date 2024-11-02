package org.example.gestionmagia.aspecto;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.gestionmagia.Almacenamiento.Almacenamiento;
import org.example.gestionmagia.Almacenamiento.AlmacenamientoRepository;
import org.example.gestionmagia.excepciones.InvalidEmailException;
import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.excepciones.PrivilegeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class Aspecto {

    @Autowired
    private AlmacenamientoRepository almacenamientoRepository;


    private final List<String> threadInfoList = new ArrayList<>();

    public List<String> getThreadInfoList() {
        return new ArrayList<>(threadInfoList);
    }

    @Pointcut("execution(* org.example.gestionmagia.Hechizos.Hechizo.lanzar*(..))")
    public void lanzarMethods() {
    }

    @Pointcut("execution(* org.example.gestionmagia.Usuario.UsuarioService.save(..)) && args(usuario)")
    public void saveUsuarioMethod(Usuario usuario) {
    }

    @Before("saveUsuarioMethod(usuario)")
    public void validateEmail(Usuario usuario) {
        String email = usuario.getCorreo();
        if (!(email.endsWith("@gmail.com") || email.endsWith("@yahoo.es") || email.endsWith("@outlook.es"))) {
            throw new InvalidEmailException("Correo electrónico no válido: " + email+ ". Debe ser un correo de gmail.com, yahoo.es o outlook.es");
        }
    }

    @Before("lanzarMethods() && args(usuario,..)")
    public void privilegiosLanzar(JoinPoint joinPoint, Usuario usuario) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        if (!usuario.isAdministrador()) {
            if (methodName.equals("lanzarHechizo4")) {
                throw new PrivilegeException("Acceso denegado: El usuario " + usuario.getNombre() + " no tiene privilegios para lanzar " + methodName);
            }
        }
    }

    @After("lanzarMethods() && args(usuario,..)")
    public void afterLanzarMethods(JoinPoint joinPoint, Usuario usuario) {
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("El método " + methodName + " ha sido invocado a las " + currentTime + " por el usuario " + usuario.getNombre());
    }

    public void captureThreadInfo(String threadName, Usuario usuario, int metodo) {
        // Guardar en la base de datos
        Almacenamiento almacenamiento = new Almacenamiento();
        almacenamiento.setUsuario(usuario);
        almacenamiento.setMetodo(metodo);
        almacenamiento.setTimestamp(LocalDateTime.now());
        almacenamientoRepository.save(almacenamiento);
    }

}