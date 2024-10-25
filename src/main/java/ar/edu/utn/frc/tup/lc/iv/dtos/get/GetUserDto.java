package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO que representa una respuesta que contiene la infromación de
 * un usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {

    /**
     * Identificador único del usuario.
     */
    private Integer id;

    /**
     * Nombre real del usuario.
     */
    private String name;

    /**
     * Apellido del usuario.
     */
    private String lastname;

    /**
     * Nombre de usuario utilizado en login.
     */
    private String username;

    /**
     * Contraseña del usuario utilizada en login.
     */
    private String password;

    /**
     * Email del usuario que se obtiene desde
     * el microservicio de Contactos y se utiliza en el login.
     */
    private String email;

    /**
     * Número de telefono del usuario que se obtiene
     * desde el microservicio de Contactos.
     */
    private String phone_number;

    /**
     * Número de DNI del usuario.
     */
    private String dni;

    /**
     * Tipo de DNI del usuario.
     */
    private String dni_type;

    /**
     * Representa sí el usuario está activo o no.
     */
    private Boolean active;

    /**
     * Dirección URL del avatar asignado al usuario.
     */
    private String avatar_url;

    /**
     * Fecha de nacimiento del usuario.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate datebirth;

    /**
     * Lista de los roles que tiene el usuario.
     */
    private String[] roles;

    /**
     * Identificador del lote asignado al usuario.
     */
    private Integer plot_id;

    /**
     * Identificador de la plataforma telegram utilizada en notificaciones.
     */
    private Integer telegram_id;
}
