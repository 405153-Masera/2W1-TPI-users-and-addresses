package ar.edu.utn.frc.tup.lc.iv.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * La clase {@code DniTypeEntity} representa un tipo de dni y
 * hace referencia a la tabla de dni_type.
 */
@Entity
@Table(name = "dni_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DniTypeEntity {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Descripción del tipo de dni.
     */
    @Column(name = "description")
    private String description;

    /**
     * Fecha que representa cuando se creó la entidad.
     */
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDatetime;

    /**
     * Fecha que representa cuando fue la última vez que se modificó la entidad.
     */
    @Column(name = "last_updated_datetime", nullable = false)
    private LocalDateTime lastUpdatedDatetime;

    /**
     * Identificador que representa el usuario que creó la entidad.
     */
    @Column(name = "created_user", nullable = false)
    private Integer createdUser;

    /**
     * Identificador que representa el usuario que modificó por última vez
     * la entidad.
     */
    @Column(name = "last_updated_user", nullable = false)
    private Integer lastUpdatedUser;
}

