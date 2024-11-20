package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.configs.TestSecurityConfig;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.helpers.UserTestHelper;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.access.AccessPost;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.access.AccessPut;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.access.RestAccess;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private RestAccess restAccessMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUserByEmailIT_Success() throws Exception {
        // Given
        GetUserDto getUserDto = UserTestHelper.createGetUserDto();
        when(userServiceMock.getUserByEmail("juapa@gmail.com")).thenReturn(getUserDto);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getByEmail/" + getUserDto.getEmail())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes())))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juapa@gmail.com"));

        verify(userServiceMock, times(1)).getUserByEmail("juapa@gmail.com");
    }

    @Test
    void getUserByEmailIT_NotFound() throws Exception{
        //Given
        when(userServiceMock.getUserByEmail("lucii@gmail")).thenReturn(null);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getByEmail/" + "lucii@gmail")
                        .accept(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(status().isNotFound());

        verify(userServiceMock, times(1)).getUserByEmail("lucii@gmail");
    }

    @Test
    void getUsersIT_Success() throws Exception{
        //Given
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        GetUserDto getUserDto = new GetUserDto();
        getUserDtoList.add(getUserDto);
        getUserDtoList.add(getUserDto);

        when(userServiceMock.getAllUsers()).thenReturn(getUserDtoList);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall")
                        .accept(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(userServiceMock, Mockito.times(1)).getAllUsers();
    }

    @Test
    void getUsersIT_BadRequest() throws Exception{
        //Given
        when(userServiceMock.getAllUsers()).thenReturn(null);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall")
                        .accept(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(status().isBadRequest());

        verify(userServiceMock, Mockito.times(1)).getAllUsers();
    }

    @Test
    void createUserIT_Success() throws Exception{
        //Given
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(10);
        getUserDto.setName("Juan");

        PostUserDto postUserDto = UserTestHelper.createPostUserDto();

        when(userServiceMock.createUser(postUserDto)).thenReturn(getUserDto);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/users/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUserDto)))

                //Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value(postUserDto.getName()));

        verify(userServiceMock, times(1)).createUser(Mockito.any(PostUserDto.class));
    }

    @Test
    void createUserIT_BadRequest() throws Exception{
        //Given
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(10);
        getUserDto.setName("Pedro");

        PostUserDto postUserDto = UserTestHelper.createPostUserDto();

        when(userServiceMock.createUser(Mockito.any(PostUserDto.class))).thenReturn(null);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/users/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUserDto)))

                //Then
                .andExpect(status().isBadRequest());

        verify(userServiceMock, times(1)).createUser(Mockito.any(PostUserDto.class));
    }

    @Test
    void getUserByPlotIdAndOwnerRole_Success() throws Exception {
        //Given
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(1);
        getUserDto.setName("Juan");
        getUserDto.setLastname("Pepes");
        getUserDto.setUsername("JuanP");
        getUserDto.setEmail("juanP@email.com");
        getUserDto.setPhone_number("123456789");
        getUserDto.setDni("12345678");
        getUserDto.setActive(true);
        getUserDto.setAvatar_url("avatar_url");
        getUserDto.setDatebirth(LocalDate.of(1997, 12, 1));
        getUserDto.setRoles(new String[]{"Gerente"});
        getUserDto.setPlot_id(new Integer[]{1});
        getUserDto.setTelegram_id(1L);
        Integer plotId = 1;
        when(userServiceMock.getUserByPlotIdAndOwnerRole(plotId)).thenReturn(getUserDto);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/owner/" + plotId)
                        .accept(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(getUserDto.getId()))
                .andExpect(jsonPath("$.name").value(getUserDto.getName()));

        verify(userServiceMock, times(1)).getUserByPlotIdAndOwnerRole(plotId);
    }

    @Test
    void getUserByPlotIdAndOwnerRole_NotFound() throws Exception {
        //Given
        Integer plotId = 1;
        when(userServiceMock.getUserByPlotIdAndOwnerRole(plotId)).thenReturn(null);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/owner/" + plotId)
                        .accept(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(status().isNotFound());

        verify(userServiceMock, times(1)).getUserByPlotIdAndOwnerRole(plotId);
    }

    @Test
    void getAllUsersByPlotId_Success() throws Exception {
        //Given
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(1);
        getUserDto.setName("Juan");
        getUserDto.setLastname("Pepes");
        getUserDto.setUsername("JuanP");
        getUserDto.setEmail("juanP@email.com");
        getUserDto.setPhone_number("123456789");
        getUserDto.setDni("12345678");
        getUserDto.setActive(true);
        getUserDto.setAvatar_url("avatar_url");
        getUserDto.setDatebirth(LocalDate.of(1997, 12, 1));
        getUserDto.setRoles(new String[]{"Gerente"});
        getUserDto.setPlot_id(new Integer[]{1});
        getUserDto.setTelegram_id(1L);
        Integer plotId = 1;
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        getUserDtoList.add(getUserDto);

        when(userServiceMock.getAllUsersByPlotId(plotId)).thenReturn(getUserDtoList);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/" + plotId)
                        .accept(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(getUserDto.getId()))
                .andExpect(jsonPath("$[0].name").value(getUserDto.getName()))
                .andExpect(jsonPath("$[0].lastname").value(getUserDto.getLastname()));

        verify(userServiceMock, times(1)).getAllUsersByPlotId(plotId);
    }
    @Test
   void getAllUsersByPlotId_NotFound() throws Exception {
     // Given
      Integer plotId = 1;
      when(userServiceMock.getAllUsersByPlotId(plotId)).thenReturn(null);

       // When & Then
    mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/" + plotId)
                    .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());

      verify(userServiceMock, times(1)).getAllUsersByPlotId(plotId);
    }

  @Test
   void getUserByIdIT_Success() throws Exception{
        //Given
     GetUserDto getUserDto = UserTestHelper.createGetUserDto();

       //When
      when(userServiceMock.getUserById(1)).thenReturn(getUserDto);

        //Then
    mockMvc.perform(MockMvcRequestBuilders.get("/users/getById/1")
                      .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
             .andExpect(jsonPath("$.name").value("Juan"));

     verify(userServiceMock, times(1)).getUserById(Mockito.anyInt());
    }

  @Test
   void getUserByIdIT_NotFound() throws Exception{
     //When
       when(userServiceMock.getUserById(1)).thenReturn(null);

       //Then
      mockMvc.perform(MockMvcRequestBuilders.get("/users/getById/1")
                    .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isNotFound());

       verify(userServiceMock, times(1)).getUserById(Mockito.anyInt());
    }

  @Test
   void getUsersByStatusIT_Success() throws Exception {
       // Given
        List<GetUserDto> getUserDtoList = new ArrayList<>();
        GetUserDto getUserDto = UserTestHelper.createGetUserDto();

       getUserDtoList.add(getUserDto);
        getUserDtoList.add(getUserDto);
       getUserDtoList.add(getUserDto);

       // When
       when(userServiceMock.getUsersByStatus(true)).thenReturn(getUserDtoList);

        // Then
      mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/status/true")
                      .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
              .andExpect(jsonPath("$.size()").value(3));

       verify(userServiceMock, times(1)).getUsersByStatus(true);
    }


   @Test
  void getUsersByStatusIT_BadRequest() throws Exception {
      // When
      when(userServiceMock.getUsersByStatus(true)).thenReturn(null);

       // Then
     mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/status/true")
                      .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest());

      verify(userServiceMock, times(1)).getUsersByStatus(true);
    }


   @Test
   void getUsersByRoleIT_Success() throws Exception {
      // Given
      List<GetUserDto> getUserDtoList = new ArrayList<>();
    GetUserDto getUserDto = UserTestHelper.createGetUserDto();

      getUserDtoList.add(getUserDto);

      // When
       when(userServiceMock.getUsersByRole(1)).thenReturn(getUserDtoList);

        // Then
      mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/role/1")
                     .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].roles[0]").value("Gerente"))
            .andExpect(jsonPath("$[0].email").value("juapa@gmail.com"));


        verify(userServiceMock, times(1)).getUsersByRole(1);
    }


   @Test
 void getUsersByRoleIT_BadRequest() throws Exception {
        // When
        when(userServiceMock.getUsersByRole(1)).thenReturn(null);

        // Then
      mockMvc.perform(MockMvcRequestBuilders.get("/users/getall/role/1")
                   .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isBadRequest());

     verify(userServiceMock, times(1)).getUsersByRole(1);
   }


   @Test
  void deleteUserIT() throws Exception{
      //When
     Mockito.doNothing().when(userServiceMock).deleteUser(1,1);

       //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1/1")
            .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent());

      verify(userServiceMock, times(1)).deleteUser(1,1);
   }

    @Test
    void updateUser_Success() throws Exception {
        Integer userId = 1;
        PutUserDto putUserDto = new PutUserDto();
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(userId);
        getUserDto.setRoles(new String[]{"Gerente"});
        getUserDto.setEmail("email@email.com");

        when(userServiceMock.updateUser(userId, putUserDto)).thenReturn(getUserDto);

        mockMvc.perform(put("/users/put/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(putUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.roles[0]").value("Gerente"))
                .andExpect(jsonPath("$.email").value("email@email.com"));

        verify(userServiceMock, times(1)).updateUser(userId, putUserDto);
    }

    @Test
    void updateUser_BadRequest() throws Exception {
        Integer userId = 1;
        PutUserDto putUserDto = new PutUserDto();

        when(userServiceMock.updateUser(userId, putUserDto)).thenReturn(null);

        mockMvc.perform(put("/users/put/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(putUserDto)))
                .andExpect(status().isBadRequest());

        verify(userServiceMock, times(1)).updateUser(userId, putUserDto);
    }

    @Test
    void updateUserOwner_Success() throws Exception {
        Integer userId = 1;
        PutUserOwnerDto putUserOwnerDto = new PutUserOwnerDto();
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(userId);

        when(userServiceMock.updateUserOwner(userId, putUserOwnerDto)).thenReturn(getUserDto);

        mockMvc.perform(put("/users/put/owner/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(putUserOwnerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));

        verify(userServiceMock, times(1)).updateUserOwner(userId, putUserOwnerDto);
    }

    @Test
    void updateUserOwner_BadRequest() throws Exception {
        Integer userId = 1;
        PutUserOwnerDto putUserOwnerDto = new PutUserOwnerDto();

        when(userServiceMock.updateUserOwner(userId, putUserOwnerDto)).thenReturn(null);

        mockMvc.perform(put("/users/put/owner/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(putUserOwnerDto)))
                .andExpect(status().isBadRequest());

        verify(userServiceMock, times(1)).updateUserOwner(userId, putUserOwnerDto);
    }

  @Test
  void getUsersByOwner_Success() throws Exception {
     Integer ownerId = 1;
       List<GetUserDto> users = new ArrayList<>();

       GetUserDto getUserDto = UserTestHelper.createGetUserDto();
     users.add(getUserDto);

     when(userServiceMock.getUsersByOwner(ownerId)).thenReturn(users);

       mockMvc.perform(MockMvcRequestBuilders.get("/users/byOwner/" + ownerId)
                     .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
             .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].id").value(getUserDto.getId()))
            .andExpect(jsonPath("$[0].roles[0]").value("Gerente"))
               .andExpect(jsonPath("$[0].email").value("juapa@gmail.com"));


        verify(userServiceMock, times(1)).getUsersByOwner(ownerId);
    }

    @Test
    void postAccessIT_Success() throws Exception {
        AccessPost accessPost = new AccessPost();
        doNothing().when(restAccessMock).postAccess(anyList());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/access")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accessPost)))
                .andExpect(status().isNoContent());

        verify(restAccessMock, times(1)).postAccess(anyList());
    }

    @Test
    void putAccessIT_Success() throws Exception {
        AccessPut accessPut = new AccessPut();
        accessPut.setDocument("document");
        doNothing().when(restAccessMock).deleteAccess(anyString());

        mockMvc.perform(put("/users/access/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accessPut)))
                .andExpect(status().isNoContent());

        verify(restAccessMock, times(1)).deleteAccess(anyString());
    }

    @Test
    void getUsersByOwnerIT_Success() throws Exception {
        Integer ownerId = 1;
        List<GetUserDto> users = Collections.singletonList(UserTestHelper.createGetUserDto());
        when(userServiceMock.getUsersByOwner(ownerId)).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byOwner/" + ownerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Juan"))
                .andExpect(jsonPath("$[0].lastname").value("Perez"))
                .andExpect(jsonPath("$[0].username").value("JuanPa"));

        verify(userServiceMock, times(1)).getUsersByOwner(ownerId);
    }

    @Test
    void getUsersByOwnerIT_NoContent() throws Exception {
        Integer ownerId = 1;
        when(userServiceMock.getUsersByOwner(ownerId)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byOwner/" + ownerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userServiceMock, times(1)).getUsersByOwner(ownerId);
    }

    @Test
    void getUsersByOwnerV2IT_Success() throws Exception {
        Integer ownerId = 1;
        List<GetUserDto> users = Collections.singletonList(UserTestHelper.createGetUserDto());
        when(userServiceMock.getUsersByOwnerWithoutOwner(ownerId)).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byOwner/" + ownerId + "/WithoutTheOwner")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Juan"))
                .andExpect(jsonPath("$[0].lastname").value("Perez"))
                .andExpect(jsonPath("$[0].username").value("JuanPa"));

        verify(userServiceMock, times(1)).getUsersByOwnerWithoutOwner(ownerId);
    }

    @Test
    void getUsersByOwnerV2IT_NoContent() throws Exception {
        Integer ownerId = 1;
        when(userServiceMock.getUsersByOwnerWithoutOwner(ownerId)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byOwner/" + ownerId + "/WithoutTheOwner")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userServiceMock, times(1)).getUsersByOwnerWithoutOwner(ownerId);
    }

    @Test
    void recoveryPasswordIT_Success() throws Exception {
        String email = "test@example.com";
        doNothing().when(userServiceMock).passwordRecovery(email);

        mockMvc.perform(put("/users/recoveryPassword/" + email)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userServiceMock, times(1)).passwordRecovery(email);
    }
}