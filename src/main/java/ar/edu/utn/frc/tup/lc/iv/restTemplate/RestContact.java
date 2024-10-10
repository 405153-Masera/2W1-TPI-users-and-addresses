package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
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

    String url = "https://my-json-server.typicode.com/405786MoroBenjamin/contact-responses/contacts";

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
}
