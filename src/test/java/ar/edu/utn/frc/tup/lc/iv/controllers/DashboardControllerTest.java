package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.configs.TestSecurityConfig;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeDistribution;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeDistributionResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeStatics;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.UserStatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DashboardController.class)
@Import(TestSecurityConfig.class)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserStatsService userStatsService;


    @Test
    void getAgeData() throws Exception {

        AgeStatics ageStatics = new AgeStatics();
        ageStatics.setAverageAge(40);
        ageStatics.setOldestAge(70);
        ageStatics.setYoungestAge(10);
        AgeDistributionResponse ageDistributionResponse = new AgeDistributionResponse();
        ageDistributionResponse.setStatics(ageStatics);


        ageDistributionResponse.setAgeDistribution(null);

        when(userStatsService.getAgeData(any(), any())).thenReturn(ageDistributionResponse);

        mockMvc.perform(get("/dashboard/age-data")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statics").value(ageStatics))
                .andExpect(jsonPath("$.statics.averageAge").value(40))
                .andExpect(jsonPath("$.statics.oldestAge").value(70))
                .andExpect(jsonPath("$.statics.youngestAge").value(10));

        verify(userStatsService).getAgeData(null, null);
    }
}
