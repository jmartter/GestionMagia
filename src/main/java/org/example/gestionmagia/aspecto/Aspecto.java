package org.example.gestionmagia.aspecto;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.exception.PrivilegeException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class Aspecto {

    @Pointcut("execution(* org.example.gestionmagia.Hechizos.Hechizo.lanzar*(..))")
    public void lanzarMethods() {
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
}