package ar.edu.utn.frc.tup.lc.iv.restTemplate.access;

import ar.edu.utn.frc.tup.lc.iv.dtos.post.BasePostUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase asociada al restTemplate para consumir el microservicio de accesos.
 */
@Data
@RequiredArgsConstructor
@Service
public class RestAccess {

    /**
     * Instancia de restTemplate para utilizar dentro de la clase.
     */
    private final RestTemplate restTemplate;

    /**
     * Dirección url donde se levanta el microservicio de accesos.
     */
    @Value("${access.service.url}")
    private String url;

    /**
     * Metodo para registrar un acceso.
     *
     * @param accessPost Lista de accesos a registrar.
     */
    public void postAccess(List<AccessPost> accessPost) {
        //EMA le agregue /owner_tenant
        restTemplate.postForEntity(url + "/owner_tenant/register", accessPost, Void.class);
    }

    /**
     * Metodo para dar de baja un acceso.
     *
     * @param document Documento a buscar.
     * @throws RuntimeException si falla la petición.
     */
    public void deleteAccess(String document) {
        try {
            restTemplate.put(url + "/owner_tenant/unsubscribe/" + document, null, Void.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete access for document: " + document, e);
        }
    }

    /**
     * Metodo para registrar un acceso.
     *
     * @param user DTO con los datos del usuario a registrar.
     */
    public void registerUserAccess(BasePostUser user) {
        AccessPost accessPost = new AccessPost();
        accessPost.setDocument(user.getDni());
        accessPost.setName(user.getName());
        accessPost.setLast_name(user.getLastname());
        accessPost.setUserId(user.getUserUpdateId());

        AccessDocumentType accessDocumentType = new AccessDocumentType();
        accessDocumentType.setDescription("DNI");
        accessPost.setDocumentType(accessDocumentType);

        AccessUserAllowedType accessUserAllowedType = new AccessUserAllowedType();
        for (String role : user.getRoles()) {
            if ("Propietario".equals(role)) { // Literal posicionada primero
                accessUserAllowedType.setDescription("Owner");
            }
        }
        if (accessUserAllowedType.getDescription() == null) {
            accessUserAllowedType.setDescription("Tenant");
        }
        accessPost.setUser_allowed_Type(accessUserAllowedType);
        accessPost.setEmail(user.getEmail());
        List<AccessPost> accessPosts = new ArrayList<>();
        accessPosts.add(accessPost);
        postAccess(accessPosts);
    }
}
