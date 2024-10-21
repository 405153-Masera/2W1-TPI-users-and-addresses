package ar.edu.utn.frc.tup.lc.iv.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;
import java.util.Map;

/**
 * Clase para el manejo de JWT.
 */
public class JwtUtil {

    /** Tiempo de expiración, es 1 dia. */
    public static final long EXPIRATION_TIME  = 86_400_000;
    /** Clave secreta. */
    private static final String SECRET_KEY = "GZ0Vj+wF6h3Q5p9c6P2pD72X8F2Fj6WjB6Y6g2Nh7D8=";  // Mantén esta clave segura

    /**
     * Metodo para generar un token JWT.
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
     * Metodo para validar token JWT.
     * @param token es el token a validar.
     * @throws IllegalArgumentException si el toquen no es válido.
     * @return un JWT.
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
     * Metodo para validar si un token expiró.
     * @param claims información dentro del token.
     * @return una fecha de expiración.
     */
    public static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
