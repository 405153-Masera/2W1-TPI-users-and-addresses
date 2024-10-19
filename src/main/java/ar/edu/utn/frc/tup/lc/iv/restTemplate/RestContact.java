package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.ContactPutRequest;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.ContactRequest;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase asociada al restTemplate para consumir el microservicio de contactos.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class RestContact {

    /**
     * Instancia de restTemplate para utilizar dentro de la clase.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Direccion url donde se levanta el microservicio de contactos.
     */
    private String url = "http://localhost:8083/contact/search";

    /**
     * Metodo para obtener una lista de contactos según una id de usuario.
     *
     * @param userId identificador de un usuario.
     * @return una lista de {@link GetContactDto}
     */
    public List<GetContactDto> getContactById(int userId) {

        // JsonNode para no tener que hacer varias clases
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url + "?userId=" + userId, JsonNode.class);

        List<GetContactDto> contacts = new ArrayList<>();

        // Si hay algo, mapeamos y lo ponemos en la lista
        if (response.getBody().isArray()) {
            for (JsonNode node : response.getBody()) {
                // tomamos los valores
                String typeContact = node.get("contactType").get("id").asText();
                String value = node.get("value").asText();

                // Creamos el dto
                GetContactDto contact = new GetContactDto();
                contact.setType_contact(Integer.parseInt(typeContact));
                contact.setValue(value);

                contacts.add(contact);
            }
        }

        return contacts;
    }

    /**
     * Metodo para obtener una lista de todos los emails.
     *
     * @return una lista de tipo {@link String}
     */
    public List<String> getAllEmails() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

        List<String> emails = new ArrayList<>();

        if (response.getBody().isArray()) {
            for (JsonNode node : response.getBody()) {
                String typeContact = node.get("contactType").get("id").asText();
                String value = node.get("value").asText();

                if (typeContact.equals("1")) {
                    emails.add(value);
                }
            }
        }

        return emails;
    }

    /**
     * Metodo para obtener una id de usuario por un parametro Email.
     *
     * @param email correo electronico de un usuario.
     * @return una id de usuario tipo {@link Integer}
     */
    public Integer getUserIdByEmail(String email) {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        if (response.getBody().isArray()) {
            for (JsonNode node : response.getBody()) {
                String contactValue = node.get("value").asText();
                String contactType = node.get("contactType").get("id").asText();

                if (contactType.equals("1") && contactValue.equals(email)) {
                    return node.get("userId").asInt();
                }
            }
        }
        return null;
    }

    /**
     * Metodo para guardar un contacto de un usuario en el microservicio de contactos.
     *
     * @param userId identificador de un usuario.
     * @param value valor del contacto a guardar.
     * @param contactType tipo de contacto a guardar (1-email , 2-telefono).
     * @return un booleano indicando si se pudo o no guardar el contacto.
     */
    public boolean saveContact(Integer userId, String value, int contactType) {

        ContactRequest contact = new ContactRequest();
        contact.setUserId(userId);
        contact.setValue(value);
        contact.setContactTypeId(contactType);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8083/contact/owner", contact, Void.class);
            return response.getStatusCode().is2xxSuccessful();

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user" + e.getMessage());
        }
    }

    /**
     * Metodo para modificar el contacto de un usuario en el microservicio de contactos.
     *
     * @param userId identificador de un usuario.
     * @param value valor del contacto a guardar.
     * @param contactType tipo de contacto a guardar (1-email , 2-telefono).
     * @return un booleano indicando si se pudo o no modificar el contacto.
     */
    public boolean updateContact(Integer userId, String value, int contactType) {
        String updateUrl = "http://localhost:8083/contact/owner/" + userId;

        ContactPutRequest contact = new ContactPutRequest();
        contact.setValue(value);
        contact.setContactTypeId(contactType);

        try {
            // Hacemos un PUT al microservicio de contactos
            restTemplate.put(updateUrl, contact);
            return true;  // si no lanza excepciones, devolvemos true indicando éxito
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user" + e.getMessage());
        }
    }
}
