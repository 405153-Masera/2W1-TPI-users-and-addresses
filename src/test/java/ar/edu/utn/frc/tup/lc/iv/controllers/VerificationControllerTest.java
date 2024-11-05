package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.services.VerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VerificationController.class)
class VerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VerificationService verificationService;

    @Test
    void isUsernameUnique() throws Exception {
        String username = "uniqueUser";
        when(verificationService.isUsernameUnique(username)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/verification/username")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUnique").value(true));
    }

    @Test
    void isEmailUnique() throws Exception {
        String email = "unique@example.com";
        when(verificationService.isEmailUnique(email)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/verification/email")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUnique").value(true));
    }

    @Test
    void isDniUnique() throws Exception {
        String dni = "12345678";
        when(verificationService.isDniUnique(dni)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/verification/dni")
                        .param("dni", dni))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUnique").value(true));
    }
}
