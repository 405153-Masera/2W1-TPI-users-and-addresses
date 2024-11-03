package ar.edu.utn.frc.tup.lc.iv.restTemplate.access;

import ar.edu.utn.frc.tup.lc.iv.dtos.post.BasePostUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private String url = "http://localhost:8090/owner_tenant";


    /**
     * Metodo para registrar un acceso.
     * @param accessPost Lista de accesos a registrar.
     */
    public void postAccess(List<AccessPost> accessPost) {
        restTemplate.postForEntity(url + "/register", accessPost, Void.class);
    }

    /**
     * Metodo para dar de baja un acceso.
     * @param document Documento a buscar.
     */
    public void deleteAccess(String document) {
        restTemplate.put("http://localhost:8090/owner-tenant" + "/unsubscribe/" + document, null, Void.class);
    }

    /**
     * Metodo para registrar un acceso.
     * @param user DTO con los datos del usuario a registrar.
     */
    public void registerUserAccess(BasePostUser user) {
        AccessPost accessPost = new AccessPost();
        accessPost.setDocument(user.getDni());
        accessPost.setName(user.getName());
        accessPost.setLast_name(user.getLastname());

        AccessDocumentType accessDocumentType = new AccessDocumentType();
        accessDocumentType.setDescription("DNI");
        accessPost.setDocumentType(accessDocumentType);

        AccessUserAllowedType accessUserAllowedType = new AccessUserAllowedType();
        for (String role : user.getRoles()) {
            if (role.equals("Propietario")) {
                accessUserAllowedType.setDescription("Propietario");
            }
        }
        if (accessUserAllowedType.getDescription() == null) {
            accessUserAllowedType.setDescription("Inquilino");
        }
        accessPost.setUser_allowed_Type(accessUserAllowedType);
        accessPost.setEmail(user.getEmail());
        List<AccessPost> accessPosts = new ArrayList<>();
        accessPosts.add(accessPost);
        postAccess(accessPosts);
    }
}
