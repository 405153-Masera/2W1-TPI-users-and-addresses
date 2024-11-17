package ar.edu.utn.frc.tup.lc.iv.restTemplate.plotOwner;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase asociada al restTemplate para consumir el microservicio de plotOwner.
 */
@Service
public class RestPlotOwner {

    /**
     * Instancia de restTemplate para utilizar dentro de la clase.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Dirección url donde se levanta el microservicio de plotOwner.
     */
    @Value("${plotowner.service.url}")
    private String url;

    /**
     * Metodo para obtener una lista de plotOwner.
     *
     * @return una lista de {@link GetPlotOwnerDto}
     * @throws EntityNotFoundException si hubo un error en la petición.
     */
    public List<GetPlotOwnerDto> getAllPlotOwner() {
        //EMa le agregue /plotOwners
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url+"/plotOwners", JsonNode.class);
        List<GetPlotOwnerDto> plotOwnerDtoList = new ArrayList<>();
        JsonNode responseBody = response.getBody();
        if (responseBody != null && responseBody.isArray()) {
            for (JsonNode node : response.getBody()) {
                GetPlotOwnerDto plotOwnerDto = new GetPlotOwnerDto();
                plotOwnerDto.setOwner_id(node.get("owner_id").asInt());
                plotOwnerDto.setPlot_id(node.get("plot_id").asInt());
                plotOwnerDtoList.add(plotOwnerDto);
            }
            return plotOwnerDtoList;
        } else {
            throw new EntityNotFoundException("Error al obtener los datos de los propietarios de los lotes");
        }
    }
}
