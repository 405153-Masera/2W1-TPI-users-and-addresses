package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para cambiar la contraseña de un usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {

    /**
     * Email del usuario.
     */
    private String email;

    /**
     * Contraseña actual.
     */
    private String currentPassword;

    /**
     * Nueva contraseña.
     */
    private String newPassword;
}
