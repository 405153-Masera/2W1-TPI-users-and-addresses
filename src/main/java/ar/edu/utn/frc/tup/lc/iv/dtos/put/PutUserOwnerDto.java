package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la solicitud para modificar un usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PutUserOwnerDto extends BasePutUser {

    /**
     * Identificador del lote asignado al usuario.
     */
    private Integer[] plot_id;
}
