package ar.edu.utn.frc.tup.lc.iv.entities;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String lastname;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String dni;

    @Column
    private Boolean active;

    @Column
    private String avatar_url;

    @Column(name = "birth_date")
    private LocalDate datebirth;

    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    @Column(name = "created_user")
    private Integer createdUser;

    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

}
