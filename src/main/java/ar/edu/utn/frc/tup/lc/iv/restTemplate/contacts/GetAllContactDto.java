package ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllContactDto {

    /** Identificador del usuario al que pertenece el contacto. */
    private Integer userId;

    /** Tipo de contacto asociado. */
    private Integer type_contact;

    /** Valor del contacto (por ejemplo, un número
     * de teléfono o correo electrónico). */
    private String value;
}
