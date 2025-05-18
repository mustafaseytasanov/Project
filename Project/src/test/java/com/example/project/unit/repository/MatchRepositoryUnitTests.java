package com.example.project.unit.repository;

import com.example.project.model.Match;
import com.example.project.model.Server;
import com.example.project.repository.MatchRepository;
import com.example.project.repository.ServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class for unit testing of MatchRepository.
 * @author Mustafa
 * @version 1.0
 */
@DataJpaTest
public class MatchRepositoryUnitTests {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Server server = new Server();
        server.setEndpoint("167.42.23.32-1337");
        server.setName("testServer");
        entityManager.persist(server);
        Match match1 = new Match();
        match1.setFragLimit(20);
        match1.setGameMode("DM");
        match1.setMap("Map1");
        match1.setTimeElapsed(23.34);
        match1.setTimeLimit(5);
        match1.setTimeStamp(LocalDateTime.of(2020, 5, 21, 11, 30, 15));
        match1.setServer(server);
        entityManager.persist(match1);
        Match match2 = new Match();
        match2.setFragLimit(15);
        match2.setGameMode("TDM");
        match2.setMap("Map2");
        match2.setTimeElapsed(2.3);
        match2.setTimeLimit(10);
        match2.setTimeStamp(LocalDateTime.of(2021, 5, 21, 11, 30, 15));
        match2.setServer(server);
        entityManager.persist(match2);
    }

    // Testing findByServerEndpointAndTimeStamp(String serverEndpoint, LocalDateTime timeStamp) method.
    @Test
    public void findByServerEndpointAndTimeStampAndReturnMatch() {
        String endpoint = "167.42.23.32-1337";
        LocalDateTime timeStamp = LocalDateTime.of(2021, 5, 21, 11, 30, 15);
        Match match = matchRepository.findByServerEndpointAndTimeStamp(endpoint, timeStamp);
        // Verifying
        assertThat(match.getMap()).isEqualTo("Map2");
    }

    // Testing findAllByServerEndpoint(String serverEndpoint) method.
    @Test
    public void findAllByServerEndpointAndReturnMatchList() {
        String endpoint = "167.42.23.32-1337";
        List<Match> matchList = matchRepository.findAllByServerEndpoint(endpoint);
        // Verifying
        assertThat(matchList.size()).isEqualTo(2);
    }

    // Testing findAllDesc() method.
    @Test
    public void findAllDescAndReturnMatchListInDescendingOrderByTimeStamp() {
        List<Match> matchList = matchRepository.findAllDesc();
        LocalDateTime localDateTime = LocalDateTime.of(2021, 5, 21, 11, 30, 15);
        // Verifying
        assertThat(matchList.size()).isEqualTo(2);
        assertThat(matchList.getFirst().getTimeStamp()).isEqualTo(localDateTime);
    }

}
