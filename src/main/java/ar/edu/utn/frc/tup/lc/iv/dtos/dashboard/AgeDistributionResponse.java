package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa la distribución de usuarios por edad.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgeDistributionResponse {

    /**
     * Distribución de usuarios por rango de edades.
     */
    private List<AgeDistribution> ageDistribution;

    /**
     * Estadísticas de edades.
     */
    private AgeStatics statics;

    /**
     * Distribución de usuarios por estado.
     */
    private UserStatusDistribution userStatusDistribution;
}
