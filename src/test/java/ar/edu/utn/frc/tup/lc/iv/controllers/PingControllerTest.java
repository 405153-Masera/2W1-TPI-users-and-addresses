package ar.edu.utn.frc.tup.lc.iv.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PingControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        PingController pingController = new PingController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(pingController).build();
    }

    @Test
    void testPongReturnsPong() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }
}