package ar.edu.utn.frc.tup.lc.iv.services.validator;

import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.exceptions.RoleUserException;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator validator;

    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        userRoleRepository = Mockito.mock(UserRoleRepository.class);
        validator = new Validator(userRoleRepository);
    }

    @Test
    void shouldNotThrowExceptionWhenUserHasRoles() {
        // Arrange
        List<UserRoleEntity> userRoles = List.of(new UserRoleEntity());

        // Act & Assert
        assertDoesNotThrow(() -> validator.validateUserByRoles(userRoles),
                "Validation should not throw an exception when roles are present");
    }

    @Test
    void shouldThrowExceptionWhenUserHasNoRoles() {
        // Arrange
        List<UserRoleEntity> userRoles = new ArrayList<>();

        // Act & Assert
        RoleUserException exception = assertThrows(RoleUserException.class,
                () -> validator.validateUserByRoles(userRoles),
                "Validation should throw RoleUserException when roles are empty");
        assertEquals("The user has no assigned roles or does not exist", exception.getMessage());
        assertEquals(404, exception.getStatus().value());
    }
}