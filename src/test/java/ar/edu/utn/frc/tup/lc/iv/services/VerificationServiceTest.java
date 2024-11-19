package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.RestContact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestContact restContact;

    @InjectMocks
    private VerificationService verificationService;


    @Test
    void usernameUnique() {
        String uniqueUsername = "Gustavo";
        String nonUniqueUsername = "Martin";

        when(userRepository.existsByUsername(uniqueUsername)).thenReturn(false);
        when(userRepository.existsByUsername(nonUniqueUsername)).thenReturn(true);

        assertTrue(verificationService.isUsernameUnique(uniqueUsername));
        assertFalse(verificationService.isUsernameUnique(nonUniqueUsername));
    }

    @Test
    void emailUnique() {
        String uniqueEmail = "unique@email.com";
        String nonUniqueEmail = "existing@email.com";
        List<String> existingEmails = Arrays.asList("existing@email.com", "other@email.com");

        when(restContact.getAllEmails()).thenReturn(existingEmails);

        assertTrue(verificationService.isEmailUnique(uniqueEmail));
        assertFalse(verificationService.isEmailUnique(nonUniqueEmail));
    }

    @Test
    void dniUnique() {
        String uniqueDni = "30750078";
        String nonUniqueDni = "87654321";

        when(userRepository.existsByDni(uniqueDni)).thenReturn(false);
        when(userRepository.existsByDni(nonUniqueDni)).thenReturn(true);

        assertTrue(verificationService.isDniUnique(uniqueDni));
        assertFalse(verificationService.isDniUnique(nonUniqueDni));
    }
}