package ar.edu.utn.frc.tup.lc.iv.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * La clase {@code UserEntity} representa un usuario.
 * Referencia a la tabla llamada "users".
 */
@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserEntity implements UserDetails {

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
     * Valor del dni del usuario.
     */
    @Column
    private String dni;

    /**
     * Identificador de tipo de dni del usuario.
     */
    @ManyToOne
    @JoinColumn(name = "dni_type_id")
    private DniTypeEntity dniType;

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
     * Fecha que representa cuando se creó el usuario.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDate;

    /**
     * Identificador que representa el usuario que creó la entidad.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Fecha que representa cuando fue la última vez que se modificó la entidad.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDate;

    /**
     * Identificador que representa el usuario que modificó por última vez
     * la entidad.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

    /**
     * Identificador de la plataforma telegram utilizada en notificaciones.
     */
    private Integer telegram_id;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
