package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * La clase {@code UserRoleEntity} representa la tabla intermedia entre user y role.
 * Referencia a la tabla llamada "userroles".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userroles")
@Builder
public class UserRoleEntity {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Identificador único de un rol representa la relación muchos a uno
     * entre role y user.
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    /**
     * Identificador único de un usuario representa la relación muchos a uno
     * entre user y role.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * Fecha que representa cuando se creó la entidad.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * Fecha que representa cuando fue la última vez que se modificó la entidad.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * Identificador que representa el usuario que creó la entidad.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Identificador que representa el usuario que modificó por última vez
     * la entidad.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;
}
