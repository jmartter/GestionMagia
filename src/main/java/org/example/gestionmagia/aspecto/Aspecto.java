// Aspecto.java
package org.example.gestionmagia.aspecto;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class Aspecto {

    @Pointcut("execution(* org.example.gestionmagia.Hechizos.Hechizo.lanzar*(..))")
    public void lanzarMethods() {
    }

    @After("lanzarMethods()")
    public void afterLanzarMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("El método " + methodName + " ha sido invocado a las " + currentTime);
    }
}