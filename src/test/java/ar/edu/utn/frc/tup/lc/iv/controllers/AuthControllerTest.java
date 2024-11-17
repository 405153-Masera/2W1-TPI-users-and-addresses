package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.ChangePassword;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostLoginDto;
import ar.edu.utn.frc.tup.lc.iv.security.jwt.JwtUtil;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @SpyBean
    private JwtUtil jwtUtil;

    private PostLoginDto credentials;
    private GetUserDto user;

    @BeforeEach
    public void setUp() {
        credentials = new PostLoginDto();
        credentials.setEmail("m@email.com");
        credentials.setPassword("password");

        user = new GetUserDto();
        user.setId(1);
        user.setName("Juan");
        user.setLastname("Pepes");
        user.setUsername("JuanP");
        user.setEmail("juanP@email.com");
        user.setPhone_number("123456789");
        user.setDni("12345678");
        user.setActive(true);
        user.setAvatar_url("avatar_url");
        user.setDatebirth(LocalDate.of(1997, 12, 1));
        user.setRoles(new String[]{"Gerente"});
        user.setPlot_id(new Integer[]{1});
        user.setTelegram_id(1l);
    }

//    @Test
//    void loginSuccess() throws Exception {
//
//       when(userServiceMock.verifyLogin(credentials)).thenReturn(user);
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"m@email.com\",\"password\":\"password\"}"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void loginFail() throws Exception {
//        when(userServiceMock.verifyLogin(credentials)).thenReturn(null);
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"mas@email.com\",\"password\":\"password\"}"))
//                .andExpect(status().is5xxServerError())
//                .andExpect(jsonPath("$.message").value("Credenciales inválidas"));
//    }
//
//    @Test
//    void changePasswordSuccess() throws Exception {
//
//        ChangePassword changePasswordDto = new ChangePassword();
//        changePasswordDto.setCurrentPassword("currentPassword");
//        changePasswordDto.setNewPassword("newPassword");
//        Mockito.doNothing().when(userServiceMock).changePassword(changePasswordDto);
//
//        mockMvc.perform(put("/auth/changePassword")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"currentPassword\":\"currentPassword\",\"newPassword\":\"newPassword\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Contraseña actualizada exitosamente"));
//    }
}
