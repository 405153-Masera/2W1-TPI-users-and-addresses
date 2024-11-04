package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostRoleDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleServiceMock;

    @Autowired
    private ObjectMapper objectMapperMock;

    @Test
    void getRoles_Success() throws Exception{
        //Given
        List<GetRoleDto> getRoleDtoList = new ArrayList<>();
        getRoleDtoList.add(new GetRoleDto(1, "Admin"));
        getRoleDtoList.add(new GetRoleDto(2, "Security"));

        //When
        Mockito.when(roleServiceMock.getAllRoles()).thenReturn(getRoleDtoList);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/roles")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].description").value("Admin"));

        Mockito.verify(roleServiceMock, times(1)).getAllRoles();
    }

    @Test
    void getRoles_BadRequest() throws Exception{
        //When
        Mockito.when(roleServiceMock.getAllRoles()).thenReturn(null);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/roles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(roleServiceMock, times(1)).getAllRoles();
    }

    @Test
    void createRole_Success() throws Exception{
        //Given
        PostRoleDto postRoleDto = new PostRoleDto("Admin", 1);

        GetRoleDto getRoleDto = new GetRoleDto(1, "Admin");

        //When
        Mockito.when(roleServiceMock.createRole(postRoleDto)).thenReturn(getRoleDto);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapperMock.writeValueAsString(postRoleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Admin"))
                .andExpect(jsonPath("$.id").value(1));

        Mockito.verify(roleServiceMock, times(1)).createRole(postRoleDto);
    }

    @Test
    void createRole_BadRequest() throws Exception{
        //Given
        PostRoleDto postRoleDto = new PostRoleDto("Admin",1 );

        //When
        Mockito.when(roleServiceMock.createRole(Mockito.any(PostRoleDto.class))).thenReturn(null);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapperMock.writeValueAsString(postRoleDto)))
                .andExpect(status().isBadRequest());

        Mockito.verify(roleServiceMock, times(1)).createRole(Mockito.any(PostRoleDto.class));
    }
}
