package ar.edu.utn.frc.tup.lc.iv.restTemplate.notifications;

import lombok.Data;

/**
 * DTO que representa la información de una recuperación.
 */
@Data
public class RecoveryDto {

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Contraseña del usuario.
     */
    private String password;
}
