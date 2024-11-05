//package ar.edu.utn.frc.tup.lc.iv.security.jwt;
//
//import com.mysql.cj.util.StringUtils;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.net.http.HttpHeaders;
//
//public class JwtAuthenticacionFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        final String token = getTokenFromRequest(request);
//
//        if(token == null){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        final String authHeader = request.getHeader("Authorization"); //Todo: sino anda, fijarse HttpHeaders.AUTHORIZATION
//
//        if(!StringUtils.isNullOrEmpty(authHeader) || authHeader.startsWith("Bearer ")){
//            return authHeader.substring(7);
//        }
//
//        return null;
//    }
//}
