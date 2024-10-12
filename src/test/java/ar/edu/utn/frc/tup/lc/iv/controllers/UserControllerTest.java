package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.services.Implementation.UserServiceImpl;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@AutoConfigureWebMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("userServiceImpl")
    private UserService userServiceMock;

    @Autowired
    private ObjectMapper objectMapperMock;
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getUserByEmail() {
//
//        String email = "test@example.com";
//        GetUserDto getUserDto = new GetUserDto();
//        getUserDto.setEmail(email);
//
//        when(userService.getUserByEmail(anyString())).thenReturn(getUserDto);
//
//        ResponseEntity<GetUserDto> response = userController.getUserByEmail(email);
//
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(email, response.getBody().getEmail());
//    }

    @Test
    void getUsers() throws Exception{
        //Given
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        GetUserDto getUserDto = new GetUserDto();
        getUserDtoList.add(getUserDto);
        getUserDtoList.add(getUserDto);

        //When
        Mockito.when(userServiceMock.getAllUsers()).thenReturn(getUserDtoList);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        Mockito.verify(userServiceMock, Mockito.times(1)).getAllUsers();
    }

    @Test
    void createUserIT() throws Exception{
        //Given
        String[] roles = {"Admin"};
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(10);
        getUserDto.setName("Pedro");

        PostUserDto postUserDto = new PostUserDto("Pedro", "Diaz", "PepeDiaz", "123456", "pepedi@jkl", "45645456",
                "45464546", true, "", LocalDate.now(), roles);

        //When
        when(userServiceMock.createUser(postUserDto)).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapperMock.writeValueAsString(postUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value(postUserDto.getName()));

        Mockito.verify(userServiceMock, times(1)).createUser(Mockito.any(PostUserDto.class));
    }
}