package ar.edu.utn.frc.tup.lc.iv.security.UserDetails;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = mock(UserService.class);
        customUserDetailsService = new CustomUserDetailsService(userRepository, userService);
    }

    @Test
    void loadUserByUsername() {
        String username = "testUser";
        GetUserDto userDto = new GetUserDto();
        userDto.setEmail(username);
        userDto.setPassword("password");

        when(userService.getUserByEmail(username)).thenReturn(userDto);

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(userDto.getPassword(), userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        String username = "nonExistentUser";

        when(userService.getUserByEmail(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
    }
}