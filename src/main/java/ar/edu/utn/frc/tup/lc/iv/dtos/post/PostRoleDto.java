package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la solicitud para crear un rol.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRoleDto {

    /**
     * Nombre del rol.
     */
    @NotNull (message = "El rol no puede ser nulo.")
    private String description;

    /**
     * Identificador del usuario que crea el rol.
     */
    private Integer userUpdateId;
}
