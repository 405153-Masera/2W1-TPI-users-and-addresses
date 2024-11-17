package ar.edu.utn.frc.tup.lc.iv.security.jwt;

import ar.edu.utn.frc.tup.lc.iv.security.UserDetails.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación JWT.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Servicio de JWT.
     */
    private final JwtUtil jwtService;

    /**
     * Servicio de detalles de usuario.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor de la clase.
     *
     * @param jwtService Servicio de JWT.
     * @param customUserDetailsService Servicio de detalles de usuario.
     */
    public JwtAuthenticationFilter(JwtUtil jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Metodo que filtra las peticiones y verifica la autenticación.
     *
     * @param request petición HTTP.
     * @param response respuesta HTTP.
     * @param filterChain cadena de filtros.
     * @throws jakarta.servlet.ServletException si hay un error en el servlet.
     * @throws IOException si hay un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {

        // Obtener token de la cabecera
        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        // Validar formato de token y extraer JWT
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
        }

        // Validar usuario y token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = customUserDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(HttpServletRequest.class.cast(request)));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
