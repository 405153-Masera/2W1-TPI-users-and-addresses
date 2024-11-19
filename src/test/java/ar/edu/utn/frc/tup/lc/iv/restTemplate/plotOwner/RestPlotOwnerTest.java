package ar.edu.utn.frc.tup.lc.iv.restTemplate.plotOwner;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestPlotOwnerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestPlotOwner restPlotOwner;

    @BeforeEach
    void setUp() {
        restPlotOwner.setUrl("http://localhost:8080");
    }

    @Test
    void getAllPlotOwner_Success() throws Exception {
        String jsonResponse = "[{\"owner_id\": 1, \"plot_id\": 101}, {\"owner_id\": 2, \"plot_id\": 102}]";
        JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
        when(restTemplate.getForEntity("http://localhost:8080/plotOwners", JsonNode.class))
                    .thenReturn(new ResponseEntity<>(jsonNode, HttpStatus.OK));

        List<GetPlotOwnerDto> result = restPlotOwner.getAllPlotOwner();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getOwner_id());
        assertEquals(101, result.get(0).getPlot_id());
        assertEquals(2, result.get(1).getOwner_id());
        assertEquals(102, result.get(1).getPlot_id());
    }

        @Test
        void getAllPlotOwner_EntityNotFoundException() {
            when(restTemplate.getForEntity("http://localhost:8080/plotOwners", JsonNode.class))
                    .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
                restPlotOwner.getAllPlotOwner();
            });

            assertEquals("Error al obtener los datos de los propietarios de los lotes", exception.getMessage());
        }
}