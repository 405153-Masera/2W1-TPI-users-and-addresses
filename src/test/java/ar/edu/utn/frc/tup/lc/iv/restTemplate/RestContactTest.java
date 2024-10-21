package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.ContactRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestContactTest {

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private RestContact restContact;

    @Test
    void getContactById() throws Exception {

        String jsonResponse = "[{\"contactType\":{\"id\":\"1\"},\"value\":\"email@email.com\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class))).thenReturn(new ResponseEntity<>(jsonNode, HttpStatus.OK));

        List<GetContactDto> contacts = restContact.getContactById(2);

        assertNotNull(contacts);
        assertEquals(1, contacts.get(0).getType_contact());
        assertEquals("email@email.com", contacts.get(0).getValue());
    }

    @Test
    void getAllEmails() throws Exception {
        String jsonResponse = "[{\"contactType\":{\"id\":\"1\"},\"value\":\"email@email.com\"}," +
                "{\"contactType\":{\"id\":\"2\"},\"value\":\"34354353\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class))).thenReturn(new ResponseEntity<>(jsonNode, HttpStatus.OK));

        List<String> emails = restContact.getAllEmails();

        assertNotNull(emails);
        assertTrue(emails.contains("email@email.com"));
    }

    @Test
    void getUserIdByEmail() throws Exception {
        String email = "email@email.com";
        String jsonResponse = "[{\"userId\":20,\"contactType\":{\"id\":\"1\"},\"value\":\"email@email.com\"}," +
                "{\"userId\":2,\"contactType\":{\"id\":\"1\"},\"value\":\"email@email2.com\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class))).thenReturn(new ResponseEntity<>(jsonNode, HttpStatus.OK));

        Integer userId = restContact.getUserIdByEmail(email);

        assertNotNull(userId);
        assertEquals(20, userId);
    }

    @Test
    void saveContact() {
        Integer userId = 1;
        String value = "email@email.com";
        int contactType = 1;

        when(restTemplate.postForEntity(anyString(), any(ContactRequest.class), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        boolean result = restContact.saveContact(userId, value, contactType);

        assertTrue(result);
    }

    @Test
    void updateContact() {
        Integer userId = 1;
        String value = "email@email.com";
        int contactType = 1;

        doNothing().when(restTemplate).put(anyString(), any(ContactRequest.class));

        boolean result = restContact.updateContact(userId, value, contactType);
        assertTrue(result);
    }
}