package ar.edu.utn.frc.tup.lc.iv.security.UserDetails;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación de la interfaz UserDetailsService,
 * contiene la lógica de autenticación.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /** Repositorio para manejar los datos de los usuarios. */
    private final UserRepository userRepository;

    /** Servicio para la gestión de los usuarios. */
    private final UserService userService;

    /**
     * Constructor de la clase.
     *
     * @param userRepository Repositorio para manejar los datos de los usuarios.
     * @param userService Servicio para la gestión de los usuarios.
     */
    public CustomUserDetailsService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Metodo que carga un usuario por su nombre de usuario.
     *
     * @param username nombre de usuario.
     * @return un {@link UserDetails}.
     * @throws UsernameNotFoundException si no se encuentra el usuario.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            GetUserDto user = userService.getUserByEmail(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return new CustomUserDetails(user);
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("User not found", ex);
        }
    }
}
