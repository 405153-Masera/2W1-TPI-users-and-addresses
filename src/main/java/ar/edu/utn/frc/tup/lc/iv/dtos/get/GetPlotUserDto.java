package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa los ids de un lote y un usuario relacionados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPlotUserDto {

    /**
     * Identificador del lote.
     */
    private Integer plot_id;

    /**
     * Identificador del usuario.
     */
    private Integer user_id;
}
