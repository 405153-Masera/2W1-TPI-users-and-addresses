package ar.edu.utn.frc.tup.lc.iv.security.UserDetails;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * Clase que representa los detalles de un usuario.
 */
@Data
@Component
public class CustomUserDetails implements UserDetails {

    /** Usuario. */
    private final GetUserDto user;

    /**
     * Constructor.
     * @param user usuario.
     */
    public CustomUserDetails(GetUserDto user) {
        this.user = user;
    }

    /**
     * Obtiene el id del usuario.
     * @return id del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Obtiene el nombre de usuario.
     * @return nombre de usuario.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Obtiene el nombre de usuario real.
     * @return nombre de usuario real.
     */
    public String getRealUsername() {
        return user.getUsername(); }

    /**
     * Obtiene el nombre del usuario.
     * @return nombre del usuario.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Obtiene si la cuenta del usuario está bloqueada.
     * @return si la cuenta del usuario está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Obtiene si las credenciales del usuario están expiradas.
     * @return si las credenciales del usuario están expiradas.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Obtiene si el usuario está habilitado.
     * @return si el usuario está habilitado.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
