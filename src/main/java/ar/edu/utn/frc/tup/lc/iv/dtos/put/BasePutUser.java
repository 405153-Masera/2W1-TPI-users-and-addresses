package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * Clase base que representa la solicitud para actualizar un usuario.
 * No se debe instanciar directamente.
 */
@Data
public class BasePutUser {
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
    private String lastName;

    /**
     * Número de DNI del usuario.
     */
    @NotNull(message = "El tipo de DNI no puede ser nulo")
    private Integer dni_type_id;

    /**
     * Número de DNI del usuario.
     */
    @NotNull(message = "El DNI no puede ser nulo")
    @Size(min = 1, max = 11, message = "El DNI debe ser valido")
    private String dni;

    /**
     * Teléfono del usuario que se modifica mediante en el microservicio de contactos.
     */
    @NotNull(message = "El contacto no puede ser nulo")
    @Size(min = 10, max = 20, message = "El numero de teléfono debe tener un formato valido")
    private String phoneNumber;

    /**
     * Correo electrónico del usuario utilizado en el login que se modifica en el microservicio
     * de contactos.
     */
    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

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
    @Size(min = 1, message = "Debe tener al menos un rol")
    private String[] roles;

    /**
     * Identificador del usuario que crea el rol.
     */
    @NotNull
    private Integer userUpdateId;

    /**
     * Identificador de la plataforma telegram utilizada en notificaciones.
     */
    private Long telegram_id;


    /**
     * Constructor protegido para evitar instanciar la clase directamente.
     */
    protected BasePutUser() {

    }
}
