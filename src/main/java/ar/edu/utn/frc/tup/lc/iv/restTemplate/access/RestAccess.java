package ar.edu.utn.frc.tup.lc.iv.restTemplate.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * Clase asociada al restTemplate para consumir el microservicio de accesos.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class RestAccess {

    /**
     * Instancia de restTemplate para utilizar dentro de la clase.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Direccion url donde se levanta el microservicio de accesos.
     */
    private String url = "http://localhost:8090/owner_tenant";


    public void postAccess(List<AccessPost> accessPost) {
        restTemplate.postForEntity(url + "/register", accessPost, Void.class);
    }

    public void deleteAccess(String document) {
        restTemplate.put("http://localhost:8090/owner-tenant" + "/unsubscribe/" + document, null, Void.class);
    }


}
