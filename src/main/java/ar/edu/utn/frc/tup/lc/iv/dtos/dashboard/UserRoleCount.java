package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la cantidad de usuarios que tienen un rol.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleCount {

    /**
     * Nombre del rol.
     */
    private String roleName;

    /**
     * Cantidad de usuarios que tienen el rol.
     */
    private Long userCount;
}
