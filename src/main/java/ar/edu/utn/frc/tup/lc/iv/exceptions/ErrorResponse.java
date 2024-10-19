package ar.edu.utn.frc.tup.lc.iv.exceptions;

import lombok.Getter;

/**
 * Clase que representa un ErrorResponse.
 */
@Getter
public class ErrorResponse {

    /**
     * Mensaje a enviar en el error.
     */
    private String message;

    /**
     * Constructor de la clase.
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * Setter de la clase.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
