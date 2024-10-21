package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostLoginDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.times;
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
    private ObjectMapper objectMapper;

    @Test
    void getUserByEmailIT_Success() throws Exception{
        //Given
        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456",
                "lucii@gmail", "1111111", "4523545", true, "", LocalDate.now(), new String[]{"Security"},1,1);

        //When
        Mockito.when(userServiceMock.getUserByEmail("lucii@gmail")).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getByEmail/" + getUserDto.getEmail())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("lucii@gmail"));

        Mockito.verify(userServiceMock, times(1)).getUserByEmail("lucii@gmail");
    }

    @Test
    void getUserByEmailIT_NotFound() throws Exception{
        //When
        Mockito.when(userServiceMock.getUserByEmail("lucii@gmail")).thenReturn(null);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getByEmail/" + "lucii@gmail")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(userServiceMock, times(1)).getUserByEmail("lucii@gmail");
    }

    @Test
    void getUsersIT_Success() throws Exception{
        //Given
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        GetUserDto getUserDto = new GetUserDto();
        getUserDtoList.add(getUserDto);
        getUserDtoList.add(getUserDto);

        //When
        Mockito.when(userServiceMock.getAllUsers()).thenReturn(getUserDtoList);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        Mockito.verify(userServiceMock, Mockito.times(1)).getAllUsers();
    }

    @Test
    void getUsersIT_BadRequest() throws Exception{
        //When
        Mockito.when(userServiceMock.getAllUsers()).thenReturn(null);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(userServiceMock, Mockito.times(1)).getAllUsers();
    }

    @Test
    void createUserIT_Success() throws Exception{
        //Given
        String[] roles = {"Admin"};
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(10);
        getUserDto.setName("Pedro");

        PostUserDto postUserDto = new PostUserDto("Pedro", "Diaz", "PepeDiaz", "123456", "pepedi@jkl", "45645465898",
                "45464546", true, "", LocalDate.now(), roles,1,1,1);

        //When
        Mockito.when(userServiceMock.createUser(postUserDto)).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value(postUserDto.getName()));

        Mockito.verify(userServiceMock, times(1)).createUser(Mockito.any(PostUserDto.class));
    }

    @Test
    void createUserIT_BadRequest() throws Exception{
        //Given
        String[] roles = {"Admin"};
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(10);
        getUserDto.setName("Pedro");

        PostUserDto postUserDto = new PostUserDto("Pedro", "Diaz", "PepeDiaz", "123456", "pepedi@jkl", "45645465898",
                "45464546", true, "", LocalDate.now(), roles,1,1,1);

        //When
        Mockito.when(userServiceMock.createUser(Mockito.any(PostUserDto.class))).thenReturn(null);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUserDto)))
                .andExpect(status().isBadRequest());

        Mockito.verify(userServiceMock, times(1)).createUser(Mockito.any(PostUserDto.class));
    }

    @Test
    void getUserByIdIT_Success() throws Exception{
        //Given
        String[] roles = {"SuperAdmin"};
        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456", "lucii@gmail", "3515623",
                "4523545", true, "", LocalDate.now(), roles,1,1);

        //When
        Mockito.when(userServiceMock.getUserById(1)).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getById/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lucía"));

        Mockito.verify(userServiceMock, times(1)).getUserById(Mockito.anyInt());
    }

    @Test
    void getUserByIdIT_NotFound() throws Exception{
        //When
        Mockito.when(userServiceMock.getUserById(1)).thenReturn(null);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getById/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(userServiceMock, times(1)).getUserById(Mockito.anyInt());
    }

    @Test
    void getUsersByStatusIT_Success() throws Exception {
        // Given
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456", "lucii@gmail", "3515623",
                "4523545", true, "", LocalDate.now(), new String[]{"SuperAdmin"}, 1, 1);

        getUserDtoList.add(getUserDto);
        getUserDtoList.add(getUserDto);
        getUserDtoList.add(getUserDto);

        // When
        Mockito.when(userServiceMock.getUsersByStatus(true)).thenReturn(getUserDtoList);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/status/true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));

        Mockito.verify(userServiceMock, times(1)).getUsersByStatus(true);
    }


    @Test
    void getUsersByStatusIT_BadRequest() throws Exception {
        // When
        Mockito.when(userServiceMock.getUsersByStatus(true)).thenReturn(null);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/status/true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(userServiceMock, times(1)).getUsersByStatus(true);
    }


    @Test
    void getUsersByRoleIT_Success() throws Exception {
        // Given
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456", "lucii@gmail", "3515623",
                "4523545", true, "", LocalDate.now(), new String[]{"Admin"}, 1, 1);

        getUserDtoList.add(getUserDto);

        // When
        Mockito.when(userServiceMock.getUsersByRole(1)).thenReturn(getUserDtoList);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/role/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].roles[0]").value("Admin"));

        Mockito.verify(userServiceMock, times(1)).getUsersByRole(1);
    }


    @Test
    void getUsersByRoleIT_BadRequest() throws Exception {
        // When
        Mockito.when(userServiceMock.getUsersByRole(1)).thenReturn(null);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/role/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(userServiceMock, times(1)).getUsersByRole(1);
    }


    @Test
    void deleteUserIT() throws Exception{
        //When
        Mockito.doNothing().when(userServiceMock).deleteUser(1,1);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(userServiceMock, times(1)).deleteUser(1,1);
    }

    @Test
    void updateUserIT_Success() throws Exception{
        //Given
        PutUserDto putUserDto = new PutUserDto("Lucía", "Fernanda", "4523545", "3515623",
                "lucii@gmail", "", LocalDate.now(), new String[]{"Admin"},1,1);

        GetUserDto getUserDto = new GetUserDto(1, "Lucía", "Fernanda", "Lucifer", "123456",
                "lucii@gmail", "1111111", "4523545", true, "", LocalDate.now(), new String[]{"Security"},1,1);

        //When
        Mockito.when(userServiceMock.updateUser(1, putUserDto)).thenReturn(getUserDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/users/put/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone_number").value("1111111"))
                .andExpect(jsonPath("$.roles[0]").value("Security"));

        Mockito.verify(userServiceMock, times(1)).updateUser(1, putUserDto);
    }

    @Test
    void updateUserIT_BadRequest() throws Exception{
        //Given
        PutUserDto putUserDto = new PutUserDto("Lucía", "Fernanda", "4523545", "3515623",
                "lucii@gmail", "", LocalDate.now(), new String[]{"Admin"},1,1);

        //When
        Mockito.when(userServiceMock.updateUser(1, putUserDto)).thenReturn(null);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/users/put/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putUserDto)))
                .andExpect(status().isBadRequest());

        Mockito.verify(userServiceMock, times(1)).updateUser(1, putUserDto);
    }
}