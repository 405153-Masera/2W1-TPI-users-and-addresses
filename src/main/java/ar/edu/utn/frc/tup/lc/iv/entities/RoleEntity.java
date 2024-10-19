package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * La clase {@code RoleEntity} representa un rol.
 * Referencia a la tabla llamada "roles".
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {

    /**
     * Identificador único de un rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Descripción del rol.
     */
    @Column(name = "description")
    private String description;

    /**
     * Fecha que reprenta cuando se creó el rol.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * Fecha que reprenta cuando fué la última vez que se modificó la entidad.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * Identificador que representa el usuario que creó la entidad.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Itentificador que representa el usuario que modificó por ultima vez
     * la entidad.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

    /**
     * Representa la relacion uno a muchos de la entidad user y role.
     */
    @OneToMany(mappedBy = "role")
    private List<UserRoleEntity> userRoles;
}