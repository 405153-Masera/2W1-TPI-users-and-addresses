package ar.edu.utn.frc.tup.lc.iv.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Clase para el manejo de JWT.
 */
@Component
public class JwtUtil {

    /** Tiempo de expiración, es 1 día. */
    public static final long EXPIRATION_TIME = 86_400_000; // 1 día en milisegundos
    /** Clave secreta. */
    private static final String SECRET_KEY = "GZ0Vj+wF6h3Q5p9c6P2pD72X8F2Fj6WjB6Y6g2Nh7D8=";

    /**
     * Método para generar un token JWT.
     * @param username nombre de usuario.
     * @param claims información dentro del token.
     * @return un JWT.
     */
    public static String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Método para validar token JWT.
     * @param token es el token a validar.
     * @param userDetails los detalles del usuario.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String usernameToken = extractUsername(token);
        final String usernameUser = userDetails.getUsername();

        return (usernameToken.equals(usernameUser) && !isTokenExpired(token));

    }

    /**
     * Método para obtener el nombre de usuario del token JWT.
     * @param token el token JWT.
     * @return el nombre de usuario.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Método para extraer una reclamación específica del token JWT.
     * @param token el token JWT.
     * @param claimsResolver la función que extrae la reclamación.
     * @param <T> el tipo de la reclamación.
     * @return el valor de la reclamación.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = validateToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Método para validar token JWT.
     * @param token es el token a validar.
     * @return los claims extraídos del token.
     * @throws IllegalArgumentException si el token no es válido.
     */
    private Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Token no válido o manipulado.", e);
        }
    }

    /**
     * Método para comprobar si el token JWT ha expirado.
     * @param token el token JWT.
     * @return true si el token ha expirado, false en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

}
