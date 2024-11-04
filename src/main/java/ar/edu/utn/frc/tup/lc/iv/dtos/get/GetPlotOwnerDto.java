package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa los ids de un lote y su propietario relacionados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPlotOwnerDto {

    /**
     * Identificador del lote.
     */
    private Integer plot_id;

    /**
     * Identificador del propietario.
     */
    private Integer owner_id;
}
