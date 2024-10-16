package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDto {
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String name;

    @NotNull(message = "El apellido no puede ser nulo")
    @Size(min = 1, max = 50, message = "El apellido debe tener entre 1 y 50 caracteres")
    private String lastname;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Size(min = 1, max = 30, message = "El nombre de usuario debe tener entre 1 y 30 caracteres")
    private String username;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @NotNull(message = "El telefono no puede ser nulo")
    private String phone_number;

    @NotNull(message = "El DNI no puede ser nulo")
    @Size(min = 1, max = 11, message = "El DNI debe ser valido")
    private String dni;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean active;

    private String avatar_url;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate datebirth;

    @NotNull(message = "Los roles no pueden ser nulos")
    private String[] roles ;

    private Integer userUpdateId;

}
