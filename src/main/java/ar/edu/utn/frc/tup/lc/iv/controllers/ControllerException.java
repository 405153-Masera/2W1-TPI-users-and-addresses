package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Clase de configuración de ControllerException.
 */
@ControllerAdvice
@Data
public class ControllerException {

    /**
     * Metodo para manejo de excepcion de clase INTERNAL_SERVER_ERROR.
     *
     * @return una respuesta de INTERNAL_SERVER_ERROR.
     * @param e excepcion.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApi> handleError(Exception e) {
        ErrorApi error = buildError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // Otros métodos de manejo de excepciones...

    /**
     * Metodo para construir un objeto error API.
     *
     * @param message mensaje de error a arrojar.
     * @param status código de HTTP.
     * @return un ErrorApi.
     */
    private ErrorApi buildError(String message, HttpStatus status) {
        return ErrorApi.builder()
                .timestamp(String.valueOf(Timestamp.from(ZonedDateTime.now().toInstant())))
                .error(status.getReasonPhrase())
                .status(status.value())
                .message(message)
                .build();
    }
}
