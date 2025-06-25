package com.example.project.unit.service;


import com.example.project.dto.InfoDTO;
import com.example.project.dto.PopularServerDTO;
import com.example.project.dto.ServerDTO;
import com.example.project.dto.StatsDTO;
import com.example.project.model.Match;
import com.example.project.model.Scoreboard;
import com.example.project.model.Server;
import com.example.project.repository.MatchRepository;
import com.example.project.repository.ServerRepository;
import com.example.project.service.impl.ServerServiceImpl;
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

/**
 * Class for unit testing of ServerService.
 * @author Mustafa
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class ServerServiceUnitTests {

    @Mock
    private ServerRepository serverRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private ServerServiceImpl serverService;

    private Server server;
    private InfoDTO infoDTO;
    private String endpoint;
    private List<Server> serverList;
    private List<Match> matchList;

    @BeforeEach
    public void setup(){
        infoDTO = new InfoDTO("ServerTest", new ArrayList<>(
                List.of("M1", "M2")
        ));
        endpoint = "84.210.26.88-1337";
        server = new Server();
        server.setName(infoDTO.getName());
        server.setEndpoint(endpoint);
        Server server1 = new Server("62.210.26.88-1337", "ServerTest1",
                List.of("M1", "M2"));
        Server server2 = new Server("62.210.26.88-1390", "ServerTest2",
                List.of("M3", "M4"));
        serverList = new ArrayList<>(
                List.of(server1, server2)
        );
        Scoreboard scoreboard1 = new Scoreboard();
        scoreboard1.setPlayerName("test player 1");
        Scoreboard scoreboard2 = new Scoreboard();
        scoreboard2.setPlayerName("test player 2");
        Match match = new Match(
                1L, LocalDateTime.of(2017, 1, 22, 15, 17, 1),
                server, "Test map", "M2", 1, 23, 1.98,
                new ArrayList<>(List.of(scoreboard1, scoreboard2))
        );
        matchList = new ArrayList<>(List.of(match));
    }

    // Testing saveOrUpdateInfo(String endpoint,InfoDTO infoDTO) method.
    @Test
    public void saveOrUpdateInfoTest() {
        // Precondition
        when(serverRepository.findByEndpoint(endpoint))
                .thenReturn(Optional.ofNullable(server));
        when(serverRepository.save(server)).thenReturn(server);

        // Action
        serverService.saveOrUpdateInfo(endpoint, infoDTO);

        // Verifying
        verify(serverRepository, times(1)).findByEndpoint(endpoint);
        verify(serverRepository, times(1)).save(server);
    }


    // Testing getOneServerInfo(String endpoint) method.
    @Test
    public void getOneServerInfoTestShouldReturnNull(){
        // Precondition
        server.setGameModes(infoDTO.getGameModes());
        when(serverRepository.findByEndpoint(endpoint))
                .thenReturn(Optional.ofNullable(server));

        // Action
        InfoDTO serverInfo = serverService.getOneServerInfo(endpoint);

        // Verifying the output
        assertThat(serverInfo.getName()).isEqualTo(server.getName());
    }

    // Testing getAllServersInfo() method.
    @Test
    public void getAllServersInfoTestShouldReturnListServerDTO() {
        // Precondition
        when(serverRepository.findAll()).thenReturn(serverList);

        // Action
        List<ServerDTO> serverDTOList = serverService.getAllServersInfo();

        // Verifying the output
        assertThat(serverDTOList.size()).isEqualTo(2);
    }


    // Testing isEmptyServer(String endpoint) method.
    @Test
    public void isEmptyServerTestShouldReturnFalse() {
        // Precondition
        when(serverRepository.findByEndpoint(endpoint)).thenReturn(Optional.ofNullable(server));

        // Action
        boolean isEmptyServer = serverService.isEmptyServer(endpoint);

        // Verifying the output
        assertThat(isEmptyServer).isFalse();
    }

    // Testing getStats(String endpoint) method.
    @Test
    public void getStatsShouldReturnStatsDTO() {
        // Precondition
        when(matchRepository.findAllByServerEndpoint(endpoint)).thenReturn(matchList);

        // Action
        StatsDTO statsDTO = serverService.getStats(endpoint);

        // Verifying the output
        assertThat(statsDTO.getTotalMatchesPlayed()).isEqualTo(1);
        assertThat(statsDTO.getMaximumMatchesPerDay()).isEqualTo(1);
        assertThat(statsDTO.getAverageMatchesPerDay()).isEqualTo(1);
        assertThat(statsDTO.getMaximumPopulation()).isEqualTo(2);
        assertThat(statsDTO.getAveragePopulation()).isEqualTo(2);
    }

    // Testing getPopularServers(int popularServersCount) method.
    @Test
    public void getPopularServersTestShouldReturnListPopularServerDTO() {
        // Precondition
        when(serverRepository.findAll()).thenReturn(serverList);
        when(matchRepository.findAllByServerEndpoint(serverList.get(0).getEndpoint()))
                .thenReturn(matchList);
        when(matchRepository.findAllByServerEndpoint(serverList.get(1).getEndpoint()))
                .thenReturn(matchList);

        // Action
        List<PopularServerDTO> popularServerDTOList = serverService.getPopularServers(10);

        // Verifying the output
        assertThat(popularServerDTOList.size()).isEqualTo(2);
    }


}
