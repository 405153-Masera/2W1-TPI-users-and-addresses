package ar.edu.utn.frc.tup.lc.iv.restTemplate.notifications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestNotificationsTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestNotifications restNotifications;

    @BeforeEach
    void setUp() {
        restNotifications.setUrl("http://localhost:8080");
    }

    @Test
    void sendRecoveryEmail_Success() {
        RecoveryDto dto = new RecoveryDto();
        when(restTemplate.postForEntity(eq("http://localhost:8080/user/password"), eq(dto), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        boolean result = restNotifications.sendRecoveryEmail(dto);

        assertTrue(result);
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
    }

    @Test
    void sendRecoveryEmail_HttpClientErrorException() {
        RecoveryDto dto = new RecoveryDto();
        when(restTemplate.postForEntity(eq("http://localhost:8080/user/password"), eq(dto), eq(Void.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            restNotifications.sendRecoveryEmail(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
    }


    @Test
    void sendRegisterEmail_Success() {
        RegisterDto dto = new RegisterDto();
        when(restTemplate.postForEntity(eq("http://localhost:8080/user/register"), eq(dto), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        boolean result = restNotifications.sendRegisterEmail(dto);

        assertTrue(result);
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
    }

    @Test
    void sendRegisterEmail_HttpClientErrorException() {
        RegisterDto dto = new RegisterDto();
        when(restTemplate.postForEntity(eq("http://localhost:8080/user/register"), eq(dto), eq(Void.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            restNotifications.sendRegisterEmail(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
    }
}