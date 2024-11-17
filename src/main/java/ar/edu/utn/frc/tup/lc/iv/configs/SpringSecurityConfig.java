package ar.edu.utn.frc.tup.lc.iv.configs;

import ar.edu.utn.frc.tup.lc.iv.security.jwt.JwtUtil;
import ar.edu.utn.frc.tup.lc.iv.security.UserDetails.CustomUserDetailsService;
import ar.edu.utn.frc.tup.lc.iv.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración de Spring Security.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    /**
     * Servicio de JWT.
     */
    private final JwtUtil jwtService;

    /**
     * Servicio de detalles de usuario.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Bean para el filtro de autenticación JWT.
     *
     * @return Filtro de autenticación JWT.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, customUserDetailsService);
    }

    /**
     * Configuración de la seguridad.
     *
     * @param http Objeto de configuración de seguridad.
     * @return Filtro de seguridad.
     * @throws IllegalStateException Si ocurre un error al configurar la seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http
                    .csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.disable())
                    .authorizeHttpRequests(authorize -> authorize
                                    .requestMatchers("/auth/**", "/v3/api-docs/**",
                                            "/swagger-ui/**",
                                            "/swagger-ui/index.html").permitAll()
                                    .anyRequest().permitAll()
                            //.anyRequest().authenticated()
                            // Lo dejo en permitAll para que no haya problemas con el front,
                            // especialmente para que no lo necesiten otros grupos
                    )
                    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

            return http.build();
        } catch (Exception e) {
            throw new IllegalStateException("Error al configurar la seguridad", e);
        }
    }

    /**
     * Bean para el administrador de autenticación.
     *
     * @param http Objeto de configuración de seguridad.
     * @return Administrador de autenticación.
     * @throws IllegalStateException Si ocurre un error al configurar la seguridad.
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) {
        try {
            AuthenticationManagerBuilder authenticationManagerBuilder =
                    http.getSharedObject(AuthenticationManagerBuilder.class);
            authenticationManagerBuilder.userDetailsService(customUserDetailsService);
            return authenticationManagerBuilder.build();
        } catch (Exception e) {
            throw new IllegalStateException("Error al configurar el AuthenticationManager", e);
        }
    }

}
