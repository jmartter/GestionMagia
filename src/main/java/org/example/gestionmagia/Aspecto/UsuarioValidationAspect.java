package org.example.gestionmagia.Aspecto;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.gestionmagia.Usuario.Usuario;
import org.example.gestionmagia.Respuesta.CustomErrorType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UsuarioValidationAspect {

    @Around("execution(* org.example.gestionmagia.Usuario.UsuarioService.save(..)) && args(usuario)")
    public Object validateAndRetry(ProceedingJoinPoint joinPoint, Usuario usuario) throws Throwable {
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomErrorType(400, "El correo no puede estar vacío"));
        }
        if (usuario.getContraseña() == null || usuario.getContraseña().isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomErrorType(400, "La contraseña no puede estar vacía"));
        }
        if (!usuario.getCorreo().endsWith("@gmail.com") &&
            !usuario.getCorreo().endsWith("@hotmail.com") &&
            !usuario.getCorreo().endsWith("@outlook.es")) {
            return ResponseEntity.badRequest().body(new CustomErrorType(400, "El correo debe terminar en @gmail.com, @hotmail.com o @outlook.es"));
        }
        return joinPoint.proceed();
    }
}