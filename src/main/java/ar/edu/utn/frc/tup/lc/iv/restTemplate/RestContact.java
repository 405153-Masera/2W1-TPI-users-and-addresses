package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.Contact;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.ContactPutRequest;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.ContactRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestContact {

    @Autowired
    private RestTemplate restTemplate;

    String url = "http://localhost:8083/contact/search";

    public List<GetContactDto> getContactById(int id) {

        // JsonNode para no tener que hacer varias clases
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url + "?userId=" + id, JsonNode.class);

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

    public List<String> getAllEmails(){
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

    public boolean saveContact(Integer userId, String value, int contactType) {

        ContactRequest contact = new ContactRequest();
        contact.setUserId(userId);
        contact.setValue(value);
        contact.setContactTypeId(contactType);


        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8083/contact/owner", contact, Void.class);
        if(response.getStatusCode().is2xxSuccessful()){
            return true; //por el momento devuelve boolean porque no devuelve nada el endpoint
        }else {
            return false;
        }
    }

    public boolean updateContact(Integer userId, String value, int contactType) {
        String updateUrl = "http://localhost:8083/contact/owner/" + userId;

        ContactPutRequest contact = new ContactPutRequest();
        contact.setValue(value);
        contact.setContactTypeId(contactType);

        try {
            // Hacemos un PUT al microservicio de contactos
            restTemplate.put(updateUrl, contact);
            return true;  // si no lanza excepciones, devolvemos true indicando éxito
        } catch (Exception e) {
            // En caso de error, se devuelve false
            e.printStackTrace();
            return false;
        }
    }
}
