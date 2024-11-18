package ar.edu.utn.frc.tup.lc.iv.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;
    private String token;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        userDetails = new User("testUser", "password", Collections.emptyList());
        Map<String, Object> claims = new HashMap<>();
        token = jwtUtil.generateToken(userDetails.getUsername(), claims);
    }

    @Test
    void generateToken() {
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void isTokenValid() {
        assertTrue(jwtUtil.isTokenValid(token, userDetails));
    }

    @Test
    void extractUsername() {
        String username = jwtUtil.extractUsername(token);
        assertEquals(userDetails.getUsername(), username);
    }
}