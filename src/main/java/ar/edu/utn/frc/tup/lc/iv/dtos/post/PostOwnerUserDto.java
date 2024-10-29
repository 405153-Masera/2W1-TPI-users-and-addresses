package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO que representa la solicitud para crear un usuario de tipo
 * Owner.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostOwnerUserDto {
    /**
     * Nombre del usuario.
     */
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String name;

    /**
     * Apellido del usuario.
     */
    @NotNull(message = "El apellido no puede ser nulo")
    @Size(min = 1, max = 50, message = "El apellido debe tener entre 1 y 50 caracteres")
    private String lastname;

    /**
     * Nombre de usuario utilizado en el login.
     */
    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Size(min = 1, max = 30, message = "El nombre de usuario debe tener entre 1 y 30 caracteres")
    private String username;

    /**
     * Contraseña del usuario utilizada en el login.
     */
    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    /**
     * Correo electronico del usuario utilizado en el login que se guarda en el microservicio
     * de contactos.
     */
    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    /**
     * Telefono del usuario que se guarda en el microservicio de contactos.
     */
    @NotNull(message = "El telefono no puede ser nulo")
    @Size(min = 10, max = 20, message = "El numero de telefono debe tener un formato valido")
    private String phone_number;

    /**
     * Numero de DNI del usuario.
     */
    @NotNull(message = "El tipo de DNI no puede ser nulo")
    private Integer dni_type_id;

    /**
     * Numero de DNI del usuario.
     */
    @NotNull(message = "El DNI no puede ser nulo")
    @Size(min = 1, max = 11, message = "El DNI debe ser valido")
    private String dni;

    /**
     * Representa sí el usuario está activo o no.
     */
    @NotNull(message = "El estado no puede ser nulo")
    private Boolean active;

    /**
     * Dirección URL del avatar asignado al usuario.
     */
    private String avatar_url;

    /**
     * Fecha de nacimiento del usuario.
     */
    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate datebirth;

    /**
     * Lista de los roles que tiene el usuario.
     */
    @NotNull(message = "Los roles no pueden ser nulos")
    private String[] roles;

    /**
     * Identificador del usuario que crea el rol.
     */
    private Integer userUpdateId;

    /**
     * Identificador del lote asignado al usuario.
     */
    private Integer[] plot_id;

    /**
     * Identificador de la plataforma telegram utilizada en notificaciones.
     */
    private Integer telegram_id;

}
