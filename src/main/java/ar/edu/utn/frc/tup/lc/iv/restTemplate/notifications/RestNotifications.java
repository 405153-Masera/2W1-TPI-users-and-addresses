package ar.edu.utn.frc.tup.lc.iv.restTemplate.notifications;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Data
@Service
@RequiredArgsConstructor
public class RestNotifications {
    private final RestTemplate restTemplate;

    String url = "http://localhost:8080/user/";


    public boolean sendRecoveryEmail(RecoveryDto dto) {
        try{
            ResponseEntity<Void> response = restTemplate.postForEntity(url + "password", dto, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            // Lanzar la excepción con la excepción original
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al enviar el email: " + e.getMessage(), e);
        }

    }

    public boolean sendRegisterEmail(RegisterDto dto) {
        try{
            ResponseEntity<Void> response = restTemplate.postForEntity(url + "register", dto, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            // Lanzar la excepción con la excepción original
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al enviar el email: " + e.getMessage(), e);
        }
    }


}
