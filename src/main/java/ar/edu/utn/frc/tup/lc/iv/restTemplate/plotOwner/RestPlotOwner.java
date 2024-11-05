package ar.edu.utn.frc.tup.lc.iv.restTemplate.plotOwner;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestPlotOwner {
    @Autowired
    private RestTemplate restTemplate;
    String url = "http://localhost:8081/plotOwners";
    public List<GetPlotOwnerDto> getAllPlotOwner() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        List<GetPlotOwnerDto> PlotOwnerDtoList = new ArrayList<>();
        JsonNode responseBody = response.getBody();
        if (responseBody != null && responseBody.isArray()) {
            for (JsonNode node : response.getBody()) {
                GetPlotOwnerDto plotOwnerDto = new GetPlotOwnerDto();
                plotOwnerDto.setOwner_id(node.get("owner_id").asInt());
                plotOwnerDto.setPlot_id(node.get("plot_id").asInt());
                PlotOwnerDtoList.add(plotOwnerDto);
            }
            return PlotOwnerDtoList;
        }
        else {
            return null;
        }
    }
}
