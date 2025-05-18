package com.example.project.unit.repository;

import com.example.project.model.Match;
import com.example.project.model.Scoreboard;
import com.example.project.model.Server;
import com.example.project.repository.ScoreboardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class for unit testing of ScoreboardRepository.
 * @author Mustafa
 * @version 1.0
 */
@DataJpaTest
public class ScoreboardRepositoryUnitTests {

    @Autowired
    private ScoreboardRepository scoreboardRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Server server = new Server();
        server.setEndpoint("167.42.23.32-1337");
        server.setName("testServer");
        entityManager.persist(server);
        Match match = new Match();
        match.setFragLimit(20);
        match.setGameMode("DM");
        match.setMap("Map1");
        match.setTimeElapsed(23.34);
        match.setTimeLimit(5);
        match.setTimeStamp(LocalDateTime.of(2020, 5, 21, 11, 30, 15));
        match.setServer(server);
        entityManager.persist(match);
        Scoreboard scoreboard1 = new Scoreboard();
        scoreboard1.setDeaths(3);
        scoreboard1.setFrags(5);
        scoreboard1.setKills(7);
        scoreboard1.setPlayerName("player1");
        scoreboard1.setMatch(match);
        entityManager.persist(scoreboard1);
        Scoreboard scoreboard2 = new Scoreboard();
        scoreboard2.setDeaths(0);
        scoreboard2.setFrags(5);
        scoreboard2.setKills(9);
        scoreboard2.setPlayerName("PLAYER1");
        scoreboard2.setMatch(match);
        entityManager.persist(scoreboard2);
        Scoreboard scoreboard3 = new Scoreboard();
        scoreboard3.setDeaths(0);
        scoreboard3.setFrags(5);
        scoreboard3.setKills(9);
        scoreboard3.setPlayerName("player2");
        scoreboard3.setMatch(match);
        entityManager.persist(scoreboard3);
    }

    // Testing findByPlayerNameIgnoreCase(String name) method.
    @Test
    public void findByPlayerNameIgnoreCaseAndReturnScoreboardList() {
        String name = "player1";
        List<Scoreboard> scoreboardList =
                scoreboardRepository.findByPlayerNameIgnoreCase(name);
        // Verifying
        assertThat(scoreboardList.size()).isEqualTo(2);
    }

}
