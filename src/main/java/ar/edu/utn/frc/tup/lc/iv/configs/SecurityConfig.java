package ar.edu.utn.frc.tup.lc.iv.configs;

import ar.edu.utn.frc.tup.lc.iv.security.jwt.JwtAuthenticacionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.AuthProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticacionFilter jwtAuthenticacionFilter;

    public SecurityConfig(AuthenticationProvider authProvider, JwtAuthenticacionFilter jwtAuthenticacionFilter) {
        this.authProvider = authProvider;
        this.jwtAuthenticacionFilter = jwtAuthenticacionFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/**").permitAll() // Permitir acceso sin autenticación a todas las rutas que empiezan con "auth/"
                        .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Desactivar el formulario de inicio de sesión
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticacionFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
