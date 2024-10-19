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
public class ContactPutRequest {

    /**
     * ID del tipo de contacto (ejemplo: email, teléfono).
     * No puede ser nulo.
     */
    @NotNull(message = "El tipo de contacto no puede ser nulo")
    private Integer contactTypeId;

    /**
     * Valor del contacto (ejemplo: dirección de email o número de teléfono).
     * No puede estar vacío.
     */
    @NotBlank(message = "El valor no puede estar vacío")
    private String value;
}
