package ar.edu.utn.frc.tup.lc.iv.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/**").permitAll() // Permitir acceso sin autenticación a todas las rutas que empiezan con "auth/"
                        .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
                )
                .formLogin(form -> form.disable()) // Desactivar el formulario de inicio de sesión
                .httpBasic(Customizer.withDefaults()) // Activar autenticación básica, si es necesario
                .build();
    }
}
