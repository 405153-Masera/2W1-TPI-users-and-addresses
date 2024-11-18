package ar.edu.utn.frc.tup.lc.iv.security.jwt;

import ar.edu.utn.frc.tup.lc.iv.security.UserDetails.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtUtil jwtUtil;
    private CustomUserDetailsService customUserDetailsService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        customUserDetailsService = mock(CustomUserDetailsService.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, customUserDetailsService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void doFilterInternal() throws ServletException, IOException {
        String token = "Bearer testToken";
        String username = "testUser";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extractUsername("testToken")).thenReturn(username);
        when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.isTokenValid("testToken", userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(userDetails, authentication.getPrincipal());
        verify(filterChain).doFilter(request, response);
    }
}