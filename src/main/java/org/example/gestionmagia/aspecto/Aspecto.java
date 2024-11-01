package org.example.gestionmagia.aspectos;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspecto {

    @Pointcut("execution(* org.example.gestionmagia.Hechizos.Hechizo.lanzar*(..))")
    public void lanzarMethods() {
    }

    @After("lanzarMethods()")
    public void afterLanzarMethods(JoinPoint joinPoint) {
        System.out.println("El método " + joinPoint.getSignature().getName() + " ha sido invocado.");
        System.out.println("Hilo actual: " + Thread.currentThread().getName());
    }
}