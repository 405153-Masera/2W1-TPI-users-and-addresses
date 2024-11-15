package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la distribución de usuarios por estado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusDistribution {

    /**
     * Cantidad de usuarios activos.
     */
    private long activeUsers;

    /**
     * Cantidad de usuarios inactivos.
     */
    private long inactiveUsers;

    /**
     * Porcentaje de usuarios activos.
     */
    private double activePercentage;

    /**
     * Porcentaje de usuarios inactivos.
     */
    private double inactivePercentage;
}
