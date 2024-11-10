package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la distribución de usuarios por rango de edades.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgeDistribution {

    /**
     * Rango de edades.
     */
    private String ageRange;

    /**
     * Cantidad de personas activas en el rango de edades.
     */
    private long activeCount;

    /**
     * Cantidad de personas inactivas en el rango de edades.
     */
    private long inactiveCount;

    /**
     * Porcentaje de personas activas en el rango de edades.
     */
    private double activePercentage;

    /**
     * Porcentaje de personas inactivas en el rango de edades.
     */
    private double inactivePercentage;
}
