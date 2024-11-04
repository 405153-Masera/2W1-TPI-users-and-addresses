package ar.edu.utn.frc.tup.lc.iv.services.validator;

import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.exceptions.RoleUserException;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Clase que se encarga de validar los roles de los usuarios.
 */
@Component
@RequiredArgsConstructor
public class Validator {

    /**
     * Repositorio para manejar los roles de los usuarios.
     */
    private final UserRoleRepository userRoleRepository;

    /**
     * Válida si el usuario tiene roles asignados.
     *
     * @param userRoles lista de roles de un usuario.
     */
    public void validateUserByRoles(List<UserRoleEntity> userRoles) {

        if (userRoles.isEmpty()) {
            throw new RoleUserException("The user has no assigned roles or does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
