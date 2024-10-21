package ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la solicitud de contacto que contiene información
 * sobre el contacto de un usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    /**
     * Identificador del usuario al que pertenece el contacto.
     */
    @NotNull(message = "El id del usuario no puede ser nulo")
    private Integer userId;

    /**
     * Identificador del tipo de contacto (por ejemplo, teléfono, email).
     */
    @NotNull(message = "El id del contacto no puede ser nulo")
    private Integer contactTypeId;

    /**
     * Valor del contacto, como el número de teléfono
     * o la dirección de correo electrónico.
     * Este campo se valida para que no esté en blanco.
     */
    @NotBlank(message = "El valor del contacto no puede estar en blanco")
    @NotNull(message = "El valor del contacto no puede ser nulo")
    private String value;
}
