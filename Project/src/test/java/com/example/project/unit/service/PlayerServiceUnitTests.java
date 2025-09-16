package com.example.project.unit.service;

import com.example.project.dto.BestPlayerDTO;
import com.example.project.dto.InfoDTO;
import com.example.project.dto.PlayerStatsDTO;
import com.example.project.model.Match;
import com.example.project.model.Scoreboard;
import com.example.project.model.Server;
import com.example.project.repository.ScoreboardRepository;
import com.example.project.repository.ServerRepository;
import com.example.project.service.impl.PlayerServiceImpl;
import com.example.project.service.impl.ServerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Class for unit testing of PlayerService.
 * @author Mustafa
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class PlayerServiceUnitTests {

    @Mock
    private ScoreboardRepository scoreboardRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private List<Scoreboard> player1Scoreboards, allScoreboards;
    private Server server;
    private Match match2;


    @BeforeEach
    public void setup(){
        server = new Server();
        server.setEndpoint("62.210.26.88-1337");
        Match match1 = new Match();
        match1.setId(1L);
        match1.setTimeStamp(LocalDateTime.of(2018, Month.APRIL, 21, 23, 59, 59));
        match1.setServer(server);
        match1.setMap("Test map");
        match1.setGameMode("test game mode");
        match1.setFragLimit(34);
        match1.setTimeLimit(54);
        match1.setTimeElapsed(5.34);
        match2 = new Match();
        match2.setId(2L);
        match2.setTimeStamp(LocalDateTime.of(2019, Month.APRIL, 21, 23, 59, 59));
        match2.setServer(server);
        match2.setMap("Test map 2");
        match2.setGameMode("test game mode 2");
        match2.setFragLimit(3);
        match2.setTimeLimit(4);
        match2.setTimeElapsed(7.04);
        Scoreboard  scoreboard1 = new Scoreboard(1L, match1, "Player 1", 8, 4, 2);
        Scoreboard  scoreboard2 = new Scoreboard(2L, match1, "Player 2", 7, 2, 12);
        Scoreboard  scoreboard3 = new Scoreboard(3L, match2, "Player 2", 8, 6, 2);
        Scoreboard  scoreboard4 = new Scoreboard(4L, match2, "Player 1", 5, 15, 12);
        List<Scoreboard> playerScoreboards = new ArrayList<>();
        playerScoreboards.add(scoreboard1);
        playerScoreboards.add(scoreboard2);
        match1.setScoreboardList(playerScoreboards);
        List<Scoreboard> playerScoreboards2 = new ArrayList<>();
        playerScoreboards2.add(scoreboard3);
        playerScoreboards2.add(scoreboard4);
        match2.setScoreboardList(playerScoreboards2);
        player1Scoreboards = new ArrayList<>();
        player1Scoreboards.add(scoreboard1);
        player1Scoreboards.add(scoreboard4);
        allScoreboards = new ArrayList<>(List.of(scoreboard1, scoreboard2, scoreboard3, scoreboard4));
    }

    // Testing getPlayerStats(String name) method.
    @Test
    public void getPlayerStatsTestShouldReturnPlayerStatsDTO() throws UnsupportedEncodingException {
        // Precondition
        String name = "player 1";
        when(scoreboardRepository.findByPlayerNameIgnoreCase(name))
                .thenReturn(player1Scoreboards);

        // Action
        PlayerStatsDTO playerStatsDTO = playerService.getPlayerStats(name);

        // Verifying the output
        assertThat(playerStatsDTO.getTotalMatchesPlayed()).isEqualTo(2);
        assertThat(playerStatsDTO.getTotalMatchesWon()).isEqualTo(1);
        assertThat(playerStatsDTO.getFavoriteServer()).isEqualTo(server.getEndpoint());
        assertThat(playerStatsDTO.getUniqueServers()).isEqualTo(1);
        assertThat(playerStatsDTO.getFavoriteGameMode()).isEqualTo(match2.getGameMode());
        assertThat(playerStatsDTO.getAverageScoreboardPercent()).isEqualTo(50.0);
        assertThat(playerStatsDTO.getMaximumMatchesPerDay()).isEqualTo(1);
        assertThat(playerStatsDTO.getAverageMatchesPerDay()).isEqualTo(1);
        assertThat(playerStatsDTO.getLastMatchPlayed()).isEqualTo("2019-04-21T23:59:59Z");
        assertThat(playerStatsDTO.getKillToDeathRatio()).isEqualTo(1.357);
    }

    // Testing getBestPlayers(int bestPlayersCount) method.
    @Test
    public void getBestPlayersTestShouldReturnListBestPlayerDTO() {
        // Precondition
        when(scoreboardRepository.findAll()).thenReturn(allScoreboards);

        // Action
        List<BestPlayerDTO> bestPlayerDTOList = playerService.getBestPlayers(10);

        // Verifying the output
        // All players played less than 10 matches so size of list is 0.
        assertThat(bestPlayerDTOList.size()).isEqualTo(0);
    }

}
