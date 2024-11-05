package ar.edu.utn.frc.tup.lc.iv.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

/**
 * Clase para el manejo de JWT.
 */
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
     * @return los claims extraídos del token.
     * @throws IllegalArgumentException si el token no es válido.
     */
    public static Claims validateToken(String token) {
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
     * Método para obtener el objeto UsernamePasswordAuthenticationToken.
     * @param token el token JWT.
     * @param userDetails los detalles del usuario.
     * @return un objeto de autenticación.
     */
//    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token, UserDetails userDetails) {
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }

    /**
     * Método para extraer el nombre de usuario del token JWT.
     * @param token el token JWT.
     * @return el nombre de usuario.
     */
    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }
}
