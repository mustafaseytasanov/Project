package com.example.project.unit.controller;

import com.example.project.controller.ReportController;
import com.example.project.dto.*;
import com.example.project.service.impl.MatchServiceImpl;
import com.example.project.service.impl.PlayerServiceImpl;
import com.example.project.service.impl.ServerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class for unit testing of ReportController.
 * @author Mustafa
 * @version 1.0
 */
@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReportControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServerServiceImpl serverService;

    @MockitoBean
    private MatchServiceImpl matchService;

    @MockitoBean
    private PlayerServiceImpl playerService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void getRecentMatchesShouldReturnEmptyList() throws Exception {
        // Precondition
        int count = 0;

        // Action
        ResultActions response = mockMvc.perform(get("/reports/recent-matches?count=" + count));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getRecentMatchesShouldReturnListReportDTO() throws Exception {
        // Precondition
        int count = 4;
        ReportDTO reportDTO = getReportDTO();
        List<ReportDTO> reportDTOList = new ArrayList<>(List.of(reportDTO));
        given(matchService.getRecentMatches(count))
                .willReturn(reportDTOList);

        // Action
        ResultActions response = mockMvc.perform(get("/reports/recent-matches?count=" + count));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(1)));
    }


    @Test
    public void getBestPlayersShouldReturnEmptyList() throws Exception {
        // Precondition
        int count = 0;

        // Action
        ResultActions response = mockMvc.perform(get("/reports/best-players?count=" + count));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getBestPlayersShouldReturnListBestPlayerDTO() throws Exception {
        // Precondition
        int count = 3;
        List<BestPlayerDTO> bestPlayerDTOList = new ArrayList<>(List.of(
                new BestPlayerDTO("Player 1", 5.43),
                new BestPlayerDTO("Player 2", 1.9),
                new BestPlayerDTO("Player 3", 1.1)
        ));
        given(playerService.getBestPlayers(count))
                .willReturn(bestPlayerDTOList);

        // Action
        ResultActions response = mockMvc.perform(get("/reports/best-players?count=" + count));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(3)));
    }

    @Test
    public void getPopularServersShouldReturnEmptyList() throws Exception {
        // Precondition
        int count = 0;

        // Action
        ResultActions response = mockMvc.perform(get("/reports/popular-servers?count=" + count));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getPopularServersShouldReturnListPopularServerDTO() throws Exception {
        // Precondition
        int count = 2;
        List<PopularServerDTO> popularServerDTOList = new ArrayList<>(List.of(
                new PopularServerDTO("62.210.26.88-1337", ">> Sniper Heaven <<",
                        24.456240),
                new PopularServerDTO("69.210.26.88-1357", ">> Unnamed server <<",
                        4.45)
        ));
        given(serverService.getPopularServers(count))
                .willReturn(popularServerDTOList);

        // Action
        ResultActions response = mockMvc.perform(get("/reports/popular-servers?count=" + count));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(2)));
    }


    private static @NotNull ReportDTO getReportDTO() {
        ScoreboardDTO scoreboardDTO = new ScoreboardDTO(
                "Player1", 20, 21, 3
        );
        ScoreboardDTO scoreboardDTO2 = new ScoreboardDTO(
                "Player2", 2, 21, 36
        );
        MatchDTO results = new MatchDTO(
                "DM-HelloWorld",
                "DM",
                20,
                20,
                12.345678,
                List.of(scoreboardDTO, scoreboardDTO2)
        );
        return new ReportDTO(
                "62.210.26.88-1337",
                "2017-01-22T15:11:12Z",
                results
        );
    }

}
