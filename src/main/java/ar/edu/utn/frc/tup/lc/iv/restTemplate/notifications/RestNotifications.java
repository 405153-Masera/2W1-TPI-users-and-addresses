package ar.edu.utn.frc.tup.lc.iv.restTemplate.notifications;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 * Clase asociada al restTemplate para consumir el microservicio de notificaciones.
 */
@Data
@Service
@RequiredArgsConstructor
public class RestNotifications {

    /**
     * Instancia de restTemplate para utilizar dentro de la clase.
     */
    private final RestTemplate restTemplate;

    /**
     * Dirección url donde se levanta el microservicio de notificaciones.
     */
    @Value("${notification.service.url}")
    private String url;

    /**
     * Metodo para enviar un email de recuperación de contraseña.
     *
     * @param dto DTO con la información del email.
     * @return true si se envió correctamente, false en caso contrario.
     * @throws ResponseStatusException si hubo un error en la petición.
     */
    public boolean sendRecoveryEmail(RecoveryDto dto) {
        //EMA agregue /user
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url + "/user/password", dto, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al enviar el email: " + e.getMessage(), e);
        }

    }

    /**
     * Metodo para enviar un email de registro.
     *
     * @param dto DTO con la información del email.
     * @return true si se envió correctamente, false en caso contrario.
     * @throws ResponseStatusException si hubo un error en la petición.
     */
    public boolean sendRegisterEmail(RegisterDto dto) {
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url + "/user/register", dto, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            // Lanzar la excepción con la excepción original
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al enviar el email: " + e.getMessage(), e);
        }
    }

}
