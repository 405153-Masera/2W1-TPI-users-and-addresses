package ar.edu.utn.frc.tup.lc.iv.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción enviada por un error de Rol.
 */
@Getter
public class RoleUserException extends RuntimeException{

    /**
     * Variable tipo HttpStatus.
     */
    private final HttpStatus status;

    /**
     * Constructor de la clase.
     */
    public RoleUserException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
