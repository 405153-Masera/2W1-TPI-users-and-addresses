package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una respuesta que contiene la infromación de
 * un rol.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRoleDto {

    /**
     * Identificador único del rol.
     */
    private Integer id;

    /**
     * Nombre del rol.
     */
    private String description;
}
