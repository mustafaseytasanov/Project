package com.example.project.unit.controller;

import com.example.project.controller.PlayerController;
import com.example.project.dto.PlayerStatsDTO;
import com.example.project.service.impl.PlayerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for unit testing of PlayerController.
 * @author Mustafa
 * @version 1.0
 */
@WebMvcTest(PlayerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PlayerControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerServiceImpl playerService;

    PlayerStatsDTO playerStatsDTO;

    @BeforeEach
    public void setup() {
        playerStatsDTO = new PlayerStatsDTO(
                5, 3, "62.210.26.88-1337", 1,
                "DM", 60, 5,
                5, "2017-01-22T15:11:12Z", 1.5
                );
    }


    @Test
    public void getPlayerStatsShouldReturnPlayerStatsDTO() throws Exception {
        // Precondition
        String name = "Test player";
        given(playerService.getPlayerStats(name)).willReturn(playerStatsDTO);

        // Action
        ResultActions response = mockMvc.perform(get("/players/{name}/stats", name));

        // Verifying
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalMatchesPlayed", is((int)playerStatsDTO.getTotalMatchesPlayed())))
                .andExpect(jsonPath("$.totalMatchesWon", is((int)playerStatsDTO.getTotalMatchesWon())))
                .andExpect(jsonPath("$.favoriteServer", is(playerStatsDTO.getFavoriteServer())))
                .andExpect(jsonPath("$.uniqueServers", is((int)playerStatsDTO.getUniqueServers())))
                .andExpect(jsonPath("$.favoriteGameMode", is(playerStatsDTO.getFavoriteGameMode())))
                .andExpect(jsonPath("$.averageScoreboardPercent", is(playerStatsDTO.getAverageScoreboardPercent())))
                .andExpect(jsonPath("$.maximumMatchesPerDay", is((int)playerStatsDTO.getMaximumMatchesPerDay())))
                .andExpect(jsonPath("$.averageMatchesPerDay", is(playerStatsDTO.getAverageMatchesPerDay())))
                .andExpect(jsonPath("$.lastMatchPlayed", is(playerStatsDTO.getLastMatchPlayed())))
                .andExpect(jsonPath("$.killToDeathRatio", is(playerStatsDTO.getKillToDeathRatio())));
    }
}