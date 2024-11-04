package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa un tipo de dni.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDniTypeDto {

    /**
     * Nombre representativo del tipo de documento (pasaporte, dni, etcétera).
     */
    private String description;
}
