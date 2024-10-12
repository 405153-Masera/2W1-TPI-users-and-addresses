package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "created_user")
    private Integer createdUser;

    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

    @OneToMany(mappedBy = "role")
    private List<UserRoleEntity> userRoles;
}