package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

    @Test
    void getUserByEmailIT() throws Exception{
        //Given
        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456",
                "lucii@gmail", "1111111", "4523545", true, "", LocalDate.now(), new String[]{"Security"});

        //When
        Mockito.when(userServiceMock.getUserByEmail("lucii@gmail")).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + getUserDto.getEmail())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("lucii@gmail"));

        Mockito.verify(userServiceMock, times(1)).getUserByEmail("lucii@gmail");
    }

    @Test
    void getUsersIT() throws Exception{
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
        Mockito.when(userServiceMock.createUser(postUserDto)).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapperMock.writeValueAsString(postUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value(postUserDto.getName()));

        Mockito.verify(userServiceMock, times(1)).createUser(Mockito.any(PostUserDto.class));
    }

    @Test
    void getUserByIdIT() throws Exception{
        //Given
        String[] roles = {"SuperAdmin"};
        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456", "lucii@gmail", "3515623",
                "4523545", true, "", LocalDate.now(), roles);

        //When
        Mockito.when(userServiceMock.getUserById(1)).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lucía"));

        Mockito.verify(userServiceMock, times(1)).getUserById(Mockito.anyInt());
    }

    @Test
    void getUsersByStatusIT() throws Exception{
        //Given
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456", "lucii@gmail", "3515623",
                "4523545", true, "", LocalDate.now(), new String[]{"SuperAdmin"});

        getUserDtoList.add(getUserDto);
        getUserDtoList.add(getUserDto);
        getUserDtoList.add(getUserDto);

        //When
        Mockito.when(userServiceMock.getUsersByStatus(true)).thenReturn(getUserDtoList);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/status")
                        .param("isActive", "true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));

        Mockito.verify(userServiceMock, times(1)).getUsersByStatus(true);
    }

    @Test
    void getUsersByRoleIT() throws Exception{
        //Given
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456", "lucii@gmail", "3515623",
                "4523545", true, "", LocalDate.now(), new String[]{"Admin"});

        getUserDtoList.add(getUserDto);

        //When
        Mockito.when(userServiceMock.getUsersByRole(1)).thenReturn(getUserDtoList);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/role")
                        .param("roleId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].roles[0]").value("Admin"));

        Mockito.verify(userServiceMock, times(1)).getUsersByRole(1);
    }

    @Test
    void deleteUserIT() throws Exception{
        //When
        Mockito.doNothing().when(userServiceMock).deleteUser(1);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(userServiceMock, times(1)).deleteUser(1);
    }

    @Test
    void updateUserIT() throws Exception{
        //Given
        PutUserDto putUserDto = new PutUserDto(1, "Lucía", "Fernanda", "4523545", "3515623",
                "lucii@gmail", "", LocalDate.now(), Arrays.asList(2));

        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456",
                "lucii@gmail", "1111111", "4523545", true, "", LocalDate.now(), new String[]{"Security"});

        //When
        Mockito.when(userServiceMock.updateUser(putUserDto)).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapperMock.writeValueAsString(putUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone_number").value("1111111"))
                .andExpect(jsonPath("$.roles[0]").value("Security"));

        Mockito.verify(userServiceMock, times(1)).updateUser(putUserDto);
    }
}