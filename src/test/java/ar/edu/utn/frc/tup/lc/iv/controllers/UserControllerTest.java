package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByEmail() {

        String email = "test@example.com";
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setEmail(email);

        when(userService.getUserByEmail(anyString())).thenReturn(Optional.of(getUserDto));

        ResponseEntity<GetUserDto> response = userController.getUserByEmail(email);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(email, response.getBody().getEmail());
    }
}