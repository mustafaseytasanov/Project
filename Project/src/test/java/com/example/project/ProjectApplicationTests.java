package com.example.project;

import com.example.project.dto.InfoDTO;
import com.example.project.dto.MatchDTO;
import com.example.project.dto.ScoreboardDTO;
import com.example.project.model.Server;
import com.example.project.repository.ServerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class ProjectApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Server server = new Server();
    InfoDTO infoDTO;
    String endpoint;
    MatchDTO matchDTO;

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }


    @BeforeEach
    public void setup(){

        infoDTO = new InfoDTO("ServerTest", new ArrayList<>(
                List.of("M1", "M2")
        ));
        endpoint = "84.210.26.88-1337";
        server.setName(infoDTO.getName());
        server.setEndpoint(endpoint);

        List<ScoreboardDTO> scoreboardList = new ArrayList<>(
                List.of(new ScoreboardDTO("PLayer1", 54, 430, 43))
        );
        matchDTO = new MatchDTO("ChessMap",
                "M3", 32, 45, 45.34, scoreboardList);

        System.out.println(postgreSQLContainer.getDatabaseName());
        System.out.println(postgreSQLContainer.getJdbcUrl());
        System.out.println(postgreSQLContainer.getUsername());
        System.out.println(postgreSQLContainer.getPassword());
    }


    // Testing methods where path request begins from "/servers".

    // Put info
    @Test
    @Order(1)
    public void putInfoShouldReturnOk() throws Exception {

        // precondition
        /* Precondition is in the above setup() method. */

        // Action and Verify
        mockMvc.perform(put(String.format("/servers/%s/info", endpoint))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(infoDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // Add match
    @Test
    @Order(2)
    public void addMatchShouldReturnOk() throws Exception {

        // Action and Verify
        String endpoint = "84.210.26.88-1337", timestamp = "2017-01-22T15:17:08Z";
        mockMvc.perform(put(String.format("/servers/%s/matches/%s", endpoint, timestamp))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matchDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // Done
    @Test
    @Order(3)
    public void getOneServerInfoShouldReturnOkAndInfoDTO() throws Exception {

        infoDTO.setGameModes(List.of("M1", "M2", "M3"));
        server.setGameModes(infoDTO.getGameModes());

        // Action and Verify
        String endpoint = "84.210.26.88-1337";
        mockMvc.perform(get(String.format("/servers/%s/info", endpoint)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(server.getName())))
                .andExpect(jsonPath("$.gameModes", is(server.getGameModes())));
    }


    @Test
    @Order(4)
    public void getAllServersInfoShouldReturnOk() throws Exception {

        // Precondition

        // Action and Verify
        mockMvc.perform(get("/servers/info"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(1)));;
    }

    @Test
    @Order(5)
    public void getMatchShouldReturnOkAndMatchDTO() throws Exception {

        // Action and Verify
        String endpoint = "84.210.26.88-1337",
        timestamp = "2017-01-22T15:17:08Z";
        mockMvc.perform(get(String.format("/servers/%s/matches/%s",
                        endpoint, timestamp)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.map", is(matchDTO.getMap())))
                .andExpect(jsonPath("$.gameMode", is(matchDTO.getGameMode())))
                .andExpect(jsonPath("$.fragLimit", is(matchDTO.getFragLimit())))
                .andExpect(jsonPath("$.timeLimit", is(matchDTO.getTimeLimit())))
                .andExpect(jsonPath("$.timeElapsed", is(matchDTO.getTimeElapsed())))
                .andExpect(jsonPath("$.scoreboard.size()", is(1)));
    }


    @Test
    @Order(6)
    public void getStatsShouldReturnOkAndStatsDTO() throws Exception {

        // Action and Verify
        String endpoint = "84.210.26.88-1337";
        mockMvc.perform(get(String.format("/servers/%s/stats", endpoint)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalMatchesPlayed", is(1)))
                .andExpect(jsonPath("$.maximumMatchesPerDay", is(1)))
                .andExpect(jsonPath("$.averageMatchesPerDay", is(1.0)))
                .andExpect(jsonPath("$.maximumPopulation", is(1)))
                .andExpect(jsonPath("$.averagePopulation", is(1.0)))
                .andExpect(jsonPath("$.top5GameModes", is(List.of("M3"))))
                .andExpect(jsonPath("$.top5Maps", is(List.of("ChessMap"))));

    }

    // Testing methods where path request begins from "/players".

    @Test
    @Order(7)
    public void getPlayerStatsShouldReturnOkAndPlayerStatsDTO() throws Exception {

        String name = "PLayer1";
        mockMvc.perform(get(String.format("/players/%s/stats",
                        name)))                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalMatchesPlayed", is(1)))
                .andExpect(jsonPath("$.totalMatchesWon", is(1)))
                .andExpect(jsonPath("$.favoriteServer", is("84.210.26.88-1337")))
                .andExpect(jsonPath("$.uniqueServers", is(1)))
                .andExpect(jsonPath("$.favoriteGameMode", is("M3")))
                .andExpect(jsonPath("$.averageScoreboardPercent", is(100.0)))
                .andExpect(jsonPath("$.maximumMatchesPerDay", is(1)))
                .andExpect(jsonPath("$.averageMatchesPerDay", is(1.0)))
                .andExpect(jsonPath("$.lastMatchPlayed", is("2017-01-22T15:17:08Z")))
                .andExpect(jsonPath("$.killToDeathRatio", is(10.0)))
        ;
    }

    // Testing methods where path request begins from "/reports".
    
    @Test
    @Order(8)
    public void getRecentMatchesWhereCountIsGreaterThanZeroShouldReturnOk() throws Exception {

        // Action and Verify
        int count = 4;
        mockMvc.perform(get(String.format("/reports/recent-matches/%d",
                        count)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @Order(9)
    public void getRecentMatchesWhereCountIsLessOrEqualZeroShouldReturnOk() throws Exception {

        // Action and Verify
        int count = -1;
        mockMvc.perform(get(String.format("/reports/recent-matches/%d", count)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[]"));
    }


    @Test
    @Order(10)
    public void getBestPlayersShouldReturnOkAndEmptyList() throws Exception {
        // Action and Verify
        int count = 4;
        mockMvc.perform(get(String.format("/reports/best-players/%d", count)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[]"));
    }

    @Test
    @Order(11)
    public void getPopularServersShouldReturnOkAndSizePopularServerDTOList() throws Exception {
        // Action and Verify
        int count = 4;
        mockMvc.perform(get(String.format("/reports/popular-servers/%d", count)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(1)));
    }

}