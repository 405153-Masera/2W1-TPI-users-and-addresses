package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * La clase {@code PlotUserEntity} representa la tabla intermedia entre user y plot.
 * Referencia a la tabla llamada "plotusers".
 */
@Entity
@Table(name = "plotusers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotUserEntity {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Identificador único de un lote.
     */
    @Column(name = "plot_id")
    private Integer plotId;

    /**
     * Identificador único de un usuario , representa la relación muchos a uno entre
     * la tabla plotusers y user.
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
