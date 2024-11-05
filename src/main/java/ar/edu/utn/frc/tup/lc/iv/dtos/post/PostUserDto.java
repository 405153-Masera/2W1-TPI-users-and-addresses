package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO que representa la solicitud para crear un usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDto extends BasePostUser {

    /**
     * Identificador del lote asignado al usuario.
     */
    private Integer plot_id;
}
