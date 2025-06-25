package com.example.project.unit.controller;


import com.example.project.controller.ServerController;
import com.example.project.dto.*;
import com.example.project.model.Match;
import com.example.project.model.Scoreboard;
import com.example.project.model.Server;
import com.example.project.service.impl.MatchServiceImpl;
import com.example.project.service.impl.ServerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for unit testing of ServerController.
 * @author Mustafa
 * @version 1.1
 */
@WebMvcTest(ServerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ServerControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServerServiceImpl serverService;

    @MockitoBean
    private MatchServiceImpl matchService;

    @Autowired
    private ObjectMapper objectMapper;

    private Server server = new Server();
    private InfoDTO infoDTO = new InfoDTO();
    private String endpoint, timestamp;
    private MatchDTO matchDTO;
    private List<ServerDTO> serverDTOList = new ArrayList<>();
    private Match match;

    @BeforeEach
    public void setup() {
        infoDTO = new InfoDTO("ServerTest", new ArrayList<>(
                List.of("M1", "M2")
        ));
        endpoint = "84.210.26.88-1337";
        timestamp = "2017-01-22T15:17:00Z";
        server = new Server();
        server.setName(infoDTO.getName());
        server.setEndpoint(endpoint);
        List<ScoreboardDTO> scoreboardList = new ArrayList<>(
                List.of(new ScoreboardDTO("PLayer1", 54, 430, 43))
        );
        matchDTO = new MatchDTO("ChessMap",
                "M3", 32, 45, 45.34, scoreboardList);
        ServerDTO serverDTO1 = new ServerDTO();
        serverDTO1.setEndpoint(endpoint);
        serverDTO1.setInfo(infoDTO);
        serverDTOList.add(serverDTO1);
        ServerDTO serverDTO2 = new ServerDTO();
        String newEndpoint = "85.210.26.88-1338";
        serverDTO2.setEndpoint(newEndpoint);
        InfoDTO newInfoDTO = new InfoDTO("SecondServerTest", new ArrayList<>(
                List.of("M1", "M3")
        ));
        serverDTO2.setInfo(newInfoDTO);
        serverDTOList.add(serverDTO2);
        match = new Match();
        match.setId(1L);
        match.setTimeStamp(LocalDateTime.of(
                2017, 1, 22, 15, 17, 1));
        match.setServer(server);
        match.setMap("Test map");
        match.setGameMode("M2");
        match.setFragLimit(1);
        match.setTimeLimit(23);
        match.setTimeElapsed(1.98);
        Scoreboard scoreboard = new Scoreboard(1L, match,
                "Test player", 2,3,4);
        List<Scoreboard> scoreboardList1 = new ArrayList<>();
        scoreboardList1.add(scoreboard);
        match.setScoreboardList(scoreboardList1);
    }

    @Test
    public void putInfoTest() throws Exception {

        // Precondition
        willDoNothing().given(serverService).saveOrUpdateInfo(endpoint, infoDTO);

        // Action
        ResultActions response = mockMvc
                .perform(put(String.format("/servers/%s/info", endpoint))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(infoDTO)));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void addMatchTestShouldReturnOk() throws Exception {

        // Precondition
        willDoNothing().given(matchService).addMatch(endpoint, timestamp, matchDTO);

        // Action
        ResultActions response = mockMvc
                .perform(put(String.format("/servers/%s/matches/%s",
                        endpoint, timestamp))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matchDTO)));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getOneServerInfoShouldReturnInfoDTO() throws Exception {
        // precondition
        given(serverService.getOneServerInfo(server.getEndpoint()))
                .willReturn(infoDTO);

        // action
        ResultActions response = mockMvc.perform(get("/servers/{endpoint}/info", endpoint));

        // verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(infoDTO.getName())))
                .andExpect(jsonPath("$.gameModes", is(infoDTO.getGameModes())));

    }

    @Test
    public void getOneServerInfoShouldReturnNotFound() throws Exception {
        // precondition
        String endpoint2 = "1.1.1.11-1111";
        given(serverService.getOneServerInfo("1.1.1.11-1111"))
                .willReturn(null);

        // action
        ResultActions response = mockMvc.perform(get("/servers/{endpoint}/info", endpoint2));

        // verify
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void getAllServerInfoShouldReturnListServerDTO() throws Exception {

        // Precondition
        given(serverService.getAllServersInfo()).willReturn(serverDTOList);

        // Action
        ResultActions response = mockMvc.perform(get("/servers/info"));

        // Verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(serverDTOList.size())));
    }

    @Test
    public void getMatchTestShouldReturnNotFound() throws Exception {
        // Precondition
        String endpoint2 = "2.2.2.2-1722";
        given(serverService.isEmptyServer(endpoint2)).willReturn(true);

        // Action
        ResultActions response = mockMvc.perform(
                get("/servers/{endpoint}/matches/{timestamp}",
                        endpoint2, timestamp));

        // Verifying
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void getMatchTestShouldReturnNotFound2() throws Exception {
        // Precondition
        given(serverService.isEmptyServer(endpoint)).willReturn(false);
        String timestamp2 = "2025-05-22T15:17:00Z";
        given(matchService.getMatch(endpoint, timestamp2)).willReturn(null);

        // Action
        ResultActions response = mockMvc.perform(
                get("/servers/{endpoint}/matches/{timestamp}",
                        endpoint, timestamp2));

        // Verifying
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void getMatchTestShouldReturnMatchDTO() throws Exception {
        // Precondition
        given(serverService.isEmptyServer(endpoint)).willReturn(false);
        given(matchService.getMatch(endpoint, timestamp)).willReturn(match);

        // Action
        ResultActions response = mockMvc.perform(
                get("/servers/{endpoint}/matches/{timestamp}",
                        endpoint, timestamp));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.map", is(match.getMap())))
                .andExpect(jsonPath("$.gameMode", is(match.getGameMode())))
                .andExpect(jsonPath("$.fragLimit", is(match.getFragLimit())))
                .andExpect(jsonPath("$.timeLimit", is(match.getTimeLimit())))
                .andExpect(jsonPath("$.timeElapsed", is(match.getTimeElapsed())));
    }

    @Test
    public void getStatsTestShouldReturnNotFound() throws Exception {
        // Precondition
        String endpoint2 = "2.2.2.2-1722";
        given(serverService.isEmptyServer(endpoint2)).willReturn(true);

        // Action
        ResultActions response = mockMvc.perform(
                get("/servers/{endpoint}/stats",
                        endpoint2));

        // Verifying
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void getStatsTestShouldReturnStatsDTO() throws Exception {
        // Precondition
        given(serverService.isEmptyServer(endpoint)).willReturn(false);
        StatsDTO statsDTO = new StatsDTO(1, 1, 1, 2,
                2, List.of("Mode 1"), List.of("Map 1"));
        given(serverService.getStats(endpoint)).willReturn(statsDTO);

        // Action
        ResultActions response = mockMvc.perform(
                get("/servers/{endpoint}/stats", endpoint));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalMatchesPlayed", is((int)statsDTO.getTotalMatchesPlayed())))
                .andExpect(jsonPath("$.maximumMatchesPerDay", is((int)statsDTO.getMaximumMatchesPerDay())))
                .andExpect(jsonPath("$.averageMatchesPerDay", is(statsDTO.getAverageMatchesPerDay())))
                .andExpect(jsonPath("$.maximumPopulation", is((int)statsDTO.getMaximumPopulation())))
                .andExpect(jsonPath("$.averagePopulation", is(statsDTO.getAveragePopulation())));
    }
}
