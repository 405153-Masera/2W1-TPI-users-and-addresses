package ar.edu.utn.frc.tup.lc.iv.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;

/**
 * Configuración de Spring para la creación de un bean RestTemplate.
 * Esta clase se encarga de definir un bean RestTemplate que será utilizado
 * para realizar llamadas a API REST en la aplicación.
 */
@Configuration
public class RestConfig {

    /**
     * Metodo default para la creación de un bean restTemplate que se
     * utiliza para hacer llamadas a API REST.
     *
     * @return una instancia de resTemplate.
     * @param builder constructor de restTemplate.
     */
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }
}
