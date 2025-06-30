package com.example.project.unit.service;

import com.example.project.dto.MatchDTO;
import com.example.project.dto.ReportDTO;
import com.example.project.dto.ScoreboardDTO;
import com.example.project.model.Match;
import com.example.project.model.Scoreboard;
import com.example.project.model.Server;
import com.example.project.repository.MatchRepository;
import com.example.project.repository.ServerRepository;
import com.example.project.service.impl.MatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Class for unit testing of MatchService.
 * @author Mustafa
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class MatchServiceUnitTests {

    @Mock
    private ServerRepository serverRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchServiceImpl matchService;

    private Server server;
    private final String endpoint = "84.210.26.88-1337";
    private Match match;
    private final String timeStamp = "2017-01-22T15:17:01Z";
    private MatchDTO matchDTO;
    private final LocalDateTime localDateTime = LocalDateTime.of(2017, 1, 22, 15, 17, 1);

    @BeforeEach
    public void setup(){
        server = new Server();
        server.setEndpoint(endpoint);
        match = new Match(1L, localDateTime,
                server, "Test map", "Test game mode", 5, 5, 10);
        matchDTO = new MatchDTO();
        matchDTO.setMap("Test map");
        matchDTO.setGameMode("Test game mode");
        matchDTO.setFragLimit(5);
        matchDTO.setTimeLimit(5);
        matchDTO.setTimeElapsed(10);
        matchDTO.setScoreboard(List.of(
                (new ScoreboardDTO("Test player", 15, 7, 7))));
    }

    // Testing addMatch(String endpoint, String timeStamp, MatchDTO matchDTO) method.
    @Test
    public void addMatchTest() {
        // Precondition
        when(serverRepository.findById(endpoint))
                .thenReturn(Optional.ofNullable(server));
        when(serverRepository.save(server)).thenReturn(server);
        lenient().when(matchRepository.save(match)).thenReturn(match);

        // Action
        matchService.addMatch(endpoint, timeStamp, matchDTO);

        // Verifying
        verify(serverRepository, times(1)).findById(endpoint);
        verify(serverRepository, times(1)).save(server);
    }

    // Testing getMatch(String endpoint, String timeStamp) method.
    @Test
    public void getMatchTestShouldReturnMatch() {
        // Precondition
        when(matchRepository.findByServerEndpointAndTimeStamp(endpoint, localDateTime))
                .thenReturn(match);

        // Action
        Match match1 = matchService.getMatch(endpoint, timeStamp);

        // Verifying
        assertThat(match1.getMap()).isEqualTo(match.getMap());
        assertThat(match1.getMap()).isEqualTo("Test map");
    }

    // Testing getRecentMatches(int recentMatchesCount) method.
    @Test
    public void getRecentMatchesTestShouldReturnListReportDTO() {
        // Precondition
        Scoreboard scoreboard =  new Scoreboard(1L, match, "Player 1", 56, 23,12);
        Match match2 =  new Match(2L,
                LocalDateTime.of(2025, 1, 2, 5,5,5),
                server, "Test map 2", "Game mode 2", 6, 7, 23.7);
        Scoreboard scoreboard2 =  new Scoreboard(2L, match2, "Player 2", 5, 3,120);
        match.setScoreboardList(List.of(scoreboard));
        match2.setScoreboardList(List.of(scoreboard2));
        List<Match> allMatchesDesc = new ArrayList<>(List.of(match2, match));
        when(matchRepository.findAllDesc()).thenReturn(allMatchesDesc);

        // Action
        List<ReportDTO> reportDTOList = matchService.getRecentMatches(10);

        // Verifying
        assertThat(reportDTOList.size()).isEqualTo(2);
        assertThat(reportDTOList.get(1).getTimeStamp()).isEqualTo(timeStamp);
        assertThat(reportDTOList.get(0).getResults().getGameMode()).isEqualTo(match2.getGameMode());
    }

}
