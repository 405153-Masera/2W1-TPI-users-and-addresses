package ar.edu.utn.frc.tup.lc.iv.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    // Tiempo de expiracion, es 1 dia
    public static final long EXPIRATION_TIME  = 86_400_000;
    private static final String SECRET_KEY = "GZ0Vj+wF6h3Q5p9c6P2pD72X8F2Fj6WjB6Y6g2Nh7D8=";  // Mantén esta clave segura


    public static String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims validateToken(String token) throws Exception {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new Exception("Token no válido o manipulado.");
        }
    }

    public static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}