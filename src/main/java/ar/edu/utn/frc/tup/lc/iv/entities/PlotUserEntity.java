package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "plotusers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "plot_id")
    private Integer plotId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "created_user")
    private Integer createdUser;

    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;
}