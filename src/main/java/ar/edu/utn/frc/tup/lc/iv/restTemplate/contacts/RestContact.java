package ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
@Service
@RequiredArgsConstructor
public class RestContact {

    /**
     * Instancia de restTemplate para utilizar dentro de la clase.
     */
    private final RestTemplate restTemplate;

    /**
     * Dirección url donde se levanta el microservicio de contactos.
     */
    @Value("${contact.service.url}")
    private String url;

    /**
     * Metodo para obtener una lista de contactos según un ID de usuario.
     *
     * @param userId identificador de un usuario.
     * @return una lista de {@link GetContactDto}
     */
    public List<GetContactDto> getContactById(int userId) {
        //Ema agregue /contact
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
                url + "/contact/search?userId=" + userId, JsonNode.class);
        List<GetContactDto> contacts = new ArrayList<>();

        GetContactDto contact;

        if (response.getBody().isArray()) {
            for (JsonNode node : response.getBody()) {

                contact = new GetContactDto();

                String typeContact = node.get("contactType").get("id").asText();
                String value = node.get("value").asText();

                contact.setType_contact(Integer.parseInt(typeContact));
                contact.setValue(value);

                contacts.add(contact);
            }
        }

        return contacts;
    }

    /**
     * Metodo para obtener una lista de todos contactos de los usuarios.
     *
     * @return una lista de {@link GetAllContactDto}
     */
    public List<GetAllContactDto> getAllContacts() {
        //Ema agregue /contact
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
                url + "/contact/search", JsonNode.class);
        List<GetAllContactDto> contacts = new ArrayList<>();

        GetAllContactDto contact;

        if (response.getBody().isArray()) {
            for (JsonNode node : response.getBody()) {

                contact = new GetAllContactDto();

                String userId = node.get("userId").asText();
                String typeContact = node.get("contactType").get("id").asText();
                String value = node.get("value").asText();

                contact.setUserId(Integer.parseInt(userId));
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
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url + "/contact/search", JsonNode.class);
        List<String> emails = new ArrayList<>();

        if (response.getBody().isArray()) {
            for (JsonNode node : response.getBody()) {
                String typeContact = node.get("contactType").get("id").asText();
                String value = node.get("value").asText();

                // Coloca la literal primero en la comparación
                if ("1".equals(typeContact)) {
                    emails.add(value);
                }
            }
        }

        return emails;
    }

    /**
     * Metodo para obtener un ID de usuario por un parámetro Email.
     *
     * @param email correo electrónico de un usuario.
     * @return una id de usuario tipo {@link Integer}
     */
    public Integer getUserIdByEmail(String email) {
        //EMA agregue /contact
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url + "/contact/search", JsonNode.class);
        if (response.getBody().isArray()) {
            for (JsonNode node : response.getBody()) {
                String contactValue = node.get("value").asText();
                String contactType = node.get("contactType").get("id").asText();

                // Coloca la literal primero en las comparaciones
                if ("1".equals(contactType) && email.equals(contactValue)) {
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
     * @param contactType tipo de contacto a guardar (1-email , 2-teléfono).
     * @param editorId identificador del usuario que guarda el contacto.
     * @return un booleano indicando si se pudo o no guardar el contacto.
     */
    public boolean saveContact(Integer userId, String value, int contactType, int editorId) {
        //EMA agregue /contact
        ContactRequest contact = new ContactRequest();
        contact.setUserId(userId);
        contact.setValue(value);
        contact.setContactTypeId(contactType);
        contact.setEditorId(editorId);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url + "/contact/owner", contact, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            // Lanzar la excepción con la excepción original
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            // Lanzar la excepción con la excepción original
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para modificar el contacto de un usuario en el microservicio de contactos.
     *
     * @param userId identificador de un usuario.
     * @param value valor del contacto a guardar.
     * @param contactType tipo de contacto a guardar (1-email , 2-teléfono).
     * @param editorId identificador del usuario que guarda el contacto.
     * @return un booleano indicando si se pudo o no modificar el contacto.
     */
    public boolean updateContact(Integer userId, String value, int contactType, int editorId) {
        //Ema agregue /contact
        String updateUrl = url + "/contact/update?userId=" + userId + "&personTypeId=3&contactTypeId=" + contactType;
        ContactPutRequest contact = new ContactPutRequest();
        contact.setUserId(userId);
        contact.setPersonTypeId(3);
        contact.setValue(value);
        contact.setContactTypeId(contactType);
        contact.setEditorId(editorId);

        try {
            // Hacemos un PUT al microservicio de contactos
            restTemplate.put(updateUrl, contact);
            return true;  // si no lanza excepciones, devolvemos true indicando éxito
        } catch (HttpClientErrorException e) {
            // Lanzar la excepción con la excepción original
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            // Lanzar la excepción con la excepción original
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while updating the contact: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para eliminar un contacto de un usuario en el microservicio de contactos.
     *
     * @param userId identificador del usuario asociado al contacto.
     * @param personTypeId identificador del tipo de persona.
     * @param contactTypeId identificador del tipo de contacto a eliminar.
     */
    public void deleteContact(Integer userId, Integer personTypeId, Integer contactTypeId) {
        String deleteUrl = url + "/contact/delete?userId=" + userId
                + "&personTypeId=" + personTypeId
                + "&contactTypeId=" + contactTypeId;

        try {
            restTemplate.delete(deleteUrl);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while deleting the contact: " + e.getMessage(), e);
        }
    }

}
