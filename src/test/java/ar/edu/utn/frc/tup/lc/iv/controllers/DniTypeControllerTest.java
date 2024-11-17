package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDniTypeDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.DniTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DniTypeController.class)
class DniTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DniTypeService dniTypeServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    void getAllDniTypes_Success() throws Exception {
//        // Given
//        List<GetDniTypeDto> dniTypeList = new ArrayList<>();
//        dniTypeList.add(new GetDniTypeDto("DNI"));
//        dniTypeList.add(new GetDniTypeDto("Pasaporte"));
//
//        // When
//        Mockito.when(dniTypeServiceMock.findAll()).thenReturn(dniTypeList);
//
//        // Then
//        mockMvc.perform(get("/dniType")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$[0].description").value("DNI"));
//
//        Mockito.verify(dniTypeServiceMock, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    void getDniTypeById_Success() throws Exception {
//        // Given
//        GetDniTypeDto dniTypeDto = new GetDniTypeDto("DNI");
//
//        // When
//        Mockito.when(dniTypeServiceMock.findById(1)).thenReturn(dniTypeDto);
//
//        // Then
//        mockMvc.perform(get("/dniType/{id}", 1)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.description").value("DNI"));
//
//        Mockito.verify(dniTypeServiceMock, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void createDniType_Success() throws Exception {
//        // Given
//        GetDniTypeDto newDniType = new GetDniTypeDto("DNI");
//        GetDniTypeDto createdDniType = new GetDniTypeDto("DNI");
//
//        // When
//        Mockito.when(dniTypeServiceMock.create(newDniType)).thenReturn(createdDniType);
//
//        // Then
//        mockMvc.perform(post("/dniType")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newDniType)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.description").value("DNI"));
//
//        Mockito.verify(dniTypeServiceMock, Mockito.times(1)).create(newDniType);
//    }
//
//    @Test
//    void updateDniType_Success() throws Exception {
//        // Given
//        GetDniTypeDto updatedDniType = new GetDniTypeDto("Updated DNI");
//        GetDniTypeDto existingDniType = new GetDniTypeDto("DNI");
//
//        // When
//        Mockito.when(dniTypeServiceMock.update(1, updatedDniType)).thenReturn(existingDniType);
//
//        // Then
//        mockMvc.perform(put("/dniType/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedDniType)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.description").value("DNI"));
//
//        Mockito.verify(dniTypeServiceMock, Mockito.times(1)).update(1, updatedDniType);
//    }
    
}