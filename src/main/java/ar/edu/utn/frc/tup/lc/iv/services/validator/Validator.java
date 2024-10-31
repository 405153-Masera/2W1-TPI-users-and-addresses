package ar.edu.utn.frc.tup.lc.iv.services.validator;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.exceptions.RoleUserException;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Validator {

    private final UserRoleRepository userRoleRepository;

    public void validateUserByRoles(List<UserRoleEntity> userRoles) {

        if (userRoles.isEmpty()) {
            throw new RoleUserException("The user has no assigned roles or does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
