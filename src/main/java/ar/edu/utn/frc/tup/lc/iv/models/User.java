package ar.edu.utn.frc.tup.lc.iv.models;

import ar.edu.utn.frc.tup.lc.iv.Enums.UserRolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String dni;
    private Boolean active;
    private String avatarUrl;
    private LocalDate birthDate;
    private Integer contact_id;
}
