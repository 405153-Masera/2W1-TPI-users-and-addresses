package ar.edu.utn.frc.tup.lc.iv.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * La clase {@code UserEntity} representa un usuario.
 * Referencia a la tabla llamada "users".
 */
@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    /**
     * Identificador único de un usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre real del usuario.
     */
    @Column
    private String name;

    /**
     * Apellido del usuario.
     */
    @Column
    private String lastname;

    /**
     * Nombre de usuario utilizado en login.
     */
    @Column
    private String username;

    /**
     * Contraseña del usuario utilizada en login.
     */
    @Column
    private String password;

    /**
     * Número de DNI del usuario.
     */
    @Column
    private String dni;

    /**
     * Representa sí el usuario está activo o no.
     */
    @Column
    private Boolean active;

    /**
     * Dirección URL del avatar asignado al usuario.
     */
    @Column
    private String avatar_url;

    /**
     * Fecha de nacimiento del usuario.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "birth_date")
    private LocalDate datebirth;

    /**
     * Fecha que reprenta cuando se creó el usuario.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * Identificador que representa el usuario que creó la entidad.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Fecha que reprenta cuando fué la última vez que se modificó la entidad.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * Itentificador que representa el usuario que moodificó por ultima vez
     * la entidad.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

    /**
     * Identificador de la plataforma telegram utilizada en notificaciones.
     */
    private Integer telegram_id;

}
