package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la solicitud para crear credenciales de acceso login.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginDto {

    /**
     * Email utilizado en la credencial de acceso.
     */
    private String email;

    /**
     * Contraseña utilizada en la credencial de acceso.
     */
    private String password;
}
