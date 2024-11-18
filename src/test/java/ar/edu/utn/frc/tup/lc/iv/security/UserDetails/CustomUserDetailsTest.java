package ar.edu.utn.frc.tup.lc.iv.security.UserDetails;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    private CustomUserDetails customUserDetails;
    private GetUserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new GetUserDto();
        userDto.setEmail("testUser");
        userDto.setPassword("password");
        customUserDetails = new CustomUserDetails(userDto);
    }

    @Test
    void getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    void getUsername() {
        assertEquals("testUser", customUserDetails.getUsername());
    }

//    @Test
//    void getRealUsername() {
//        assertEquals("testUser", customUserDetails.getRealUsername());
//    }

    @Test
    void isAccountNonExpired() {
        assertTrue(customUserDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(customUserDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(customUserDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(customUserDetails.isEnabled());
    }

    @Test
    void getUser() {
        assertEquals(userDto, customUserDetails.getUser());
    }

    @Test
    void testEquals() {
        CustomUserDetails other = new CustomUserDetails(userDto);
        assertEquals(customUserDetails, other);
    }

    @Test
    void canEqual() {
        CustomUserDetails other = new CustomUserDetails(userDto);
        assertTrue(customUserDetails.canEqual(other));
    }

    @Test
    void testHashCode() {
        CustomUserDetails other = new CustomUserDetails(userDto);
        assertEquals(customUserDetails.hashCode(), other.hashCode());
    }

    @Test
    void testToString() {
        String expected = "CustomUserDetails(user=" + userDto + ")";
        assertEquals(expected, customUserDetails.toString());
    }
}