package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * DTO que representa la solicitud para crear un usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostUserDto extends BasePostUser {

    /**
     * Identificador del lote asignado al usuario.
     */
    private Integer plot_id;
}
