package com.example.project.controller;

import com.example.project.dto.InfoDTO;
import com.example.project.dto.ServerDTO;
import com.example.project.model.Match;
import com.example.project.model.Server;
import com.example.project.service.ServerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class  ServerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ServerService serverService;


    // test-put-info
    // test-add-match
    // test-get-match
    // test-get-stats


    // Temporarily gameModes = null. Unfinished!
    // test-get-info-one-server
    @Test
    void getOneServerInfoShouldReturnOneServer() throws Exception {

        Mockito.when(this.serverService.getOneServerInfo("163.42.78.32-5547"))
                .thenReturn(getServersDTO().get(1).getInfo());

        mvc.perform(get("/servers/163.42.78.32-5547/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Server2"));
    }

    @Test
    void getAllServersInfoShouldReturnAllServersDTO() throws Exception {
        Mockito.when(this.serverService.getAllServersInfo()).thenReturn(getServersDTO());

        mvc.perform(get("/servers/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private List<ServerDTO> getServersDTO() {

        InfoDTO infoDTO1 = new InfoDTO("Server1",
                new ArrayList<>(List.of("DM", "TDM")));
        ServerDTO one = new ServerDTO("167.42.23.32-1337", infoDTO1);
        InfoDTO infoDTO2 = new InfoDTO("Server2",
                new ArrayList<>(List.of("TDM", "PD")));
        ServerDTO two = new ServerDTO("163.42.78.32-5547", infoDTO2);
        return List.of(one, two);
    }

}
