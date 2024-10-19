package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la información de un contacto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetContactDto {

    /** Tipo de contacto asociado. */
    private Integer type_contact;

    /** Valor del contacto (por ejemplo, un número
     * de teléfono o correo electrónico). */
    private String value;
}
