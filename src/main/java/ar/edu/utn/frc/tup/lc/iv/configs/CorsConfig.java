package ar.edu.utn.frc.tup.lc.iv.configs;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de Cors.
 * Esta clase se encarga de la configuración de Cors para evitar conflictos
 * con consultas HTTP.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Clase principal de la configuración de Cors.
     * @param registry registro del Cors.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
