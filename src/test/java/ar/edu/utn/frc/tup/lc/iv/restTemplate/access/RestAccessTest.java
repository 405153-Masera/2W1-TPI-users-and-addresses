package ar.edu.utn.frc.tup.lc.iv.restTemplate.access;

import ar.edu.utn.frc.tup.lc.iv.dtos.post.BasePostUser;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestAccessTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestAccess restAccess;

    private static final String BASE_URL = "http:localhost:8090/owner-tenant";

    @BeforeEach
    void setUp() {
        restAccess.setUrl(BASE_URL);
    }

    @Test
    void postAccess() {
        List<AccessPost> accessPosts = new ArrayList<>();
        AccessPost accessPost = new AccessPost();
        accessPost.setDocument("12345678");
        accessPost.setName("John");
        accessPost.setLast_name("Doe");
        accessPosts.add(accessPost);

        when(restTemplate.postForEntity(
                eq(BASE_URL + "/owner_tenant/register"),
                eq(accessPosts),
                eq(Void.class)))
                .thenReturn(ResponseEntity.ok().build());

        restAccess.postAccess(accessPosts);
        verify(restTemplate).postForEntity(
                eq(BASE_URL + "/owner_tenant/register"),
                eq(accessPosts),
                eq(Void.class)
        );
    }

    @Test
    void deleteAccess() {
        String document = "12345678";
        restAccess.deleteAccess(document);
        verify(restTemplate).put(
                eq(BASE_URL + "/owner_tenant/unsubscribe/" + document),
                isNull(),
                eq(Void.class)
        );
    }

    @Test
    void registerUserAccess() {
        BasePostUser user = createBasePostUser("12345678", "John", "Doe", "john@example.com", new String[]{"Propietario"});
        ArgumentCaptor<List<AccessPost>> accessPostCaptor = ArgumentCaptor.forClass(List.class);

        restAccess.registerUserAccess(user);
        verify(restTemplate).postForEntity(
                eq(BASE_URL + "/owner_tenant/register"),
                accessPostCaptor.capture(),
                eq(Void.class)
        );

        List<AccessPost> capturedAccessPosts = accessPostCaptor.getValue();
        assertEquals(1, capturedAccessPosts.size());
        AccessPost capturedPost = capturedAccessPosts.get(0);

        assertEquals("12345678", capturedPost.getDocument());
        assertEquals("John", capturedPost.getName());
        assertEquals("Doe", capturedPost.getLast_name());
        assertEquals("DNI", capturedPost.getDocumentType().getDescription());
        assertEquals("Owner", capturedPost.getUser_allowed_Type().getDescription());
        assertEquals("john@example.com", capturedPost.getEmail());
    }

    private BasePostUser createBasePostUser(String dni, String name, String lastname, String email, String[] roles) {
        BasePostUser user = new PostOwnerUserDto();
        user.setDni(dni);
        user.setName(name);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setRoles(roles);
        return user;
    }
}