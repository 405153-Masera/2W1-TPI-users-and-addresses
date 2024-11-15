package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la estadística de edades.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgeStatics {

    /**
     * Cantidad total de usuarios.
     */
    private long totalUsers;

    /**
     * Promedio de edades.
     */
    private double averageAge;

    /**
     * Edad más joven.
     */
    private int youngestAge;

    /**
     * Edad más grande.
     */
    private int oldestAge;
}
