package com.example.project.controller;

import com.example.project.dto.MatchDTO;
import com.example.project.dto.ScoreboardDTO;
import com.example.project.dto.ServerDTO;
import com.example.project.dto.StatsDTO;
import com.example.project.model.Match;
import com.example.project.model.Server;
import com.example.project.repository.ServerRepository;
import com.example.project.service.MatchService;
import com.example.project.service.ServerService;
import com.example.project.exception.ServerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/servers")
public class ServerController {

    private final ServerService serverService;
    private final MatchService matchService;

    @Autowired
    public ServerController(ServerService serverService, MatchService matchService, ServerRepository serverRepository) {
        this.serverService = serverService;
        this.matchService = matchService;
    }

    @PutMapping("/{endpoint}/info")
    public ResponseEntity<Void> putInfo(@PathVariable String endpoint,
                                     @RequestBody ServerDTO serverDTO) { // DTO
        serverService.saveOrUpdateInfo(endpoint, serverDTO);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{endpoint}/matches/{timestamp}")
    public ResponseEntity<Void> addMatch(@PathVariable String endpoint,
                                         @PathVariable String timestamp,
                                         @RequestBody MatchDTO matchDTO) {
        try {
            matchService.addMatch(endpoint, timestamp, matchDTO);
            return ResponseEntity.ok().build();
        } catch (ServerNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{endpoint}/info")
    public ResponseEntity<Server> getInfo(@PathVariable String endpoint) {
        Server server = serverService.getServer(endpoint);
        if (server != null) {
            return ResponseEntity.ok(server);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<List<Server>> getInfo() {
        return ResponseEntity.ok(serverService.getAllServers());
    }

    @GetMapping("/{endpoint}/matches/{timestamp}")
    public ResponseEntity<MatchDTO> getMatch(
            @PathVariable String endpoint,
            @PathVariable String timestamp) {

        if (serverService.IsExistsServer(endpoint)) {
            return ResponseEntity.notFound().build();
        }

        Match match = matchService.getMatch(endpoint, timestamp);

        if (match == null) {
            return ResponseEntity.notFound().build();
        }

        MatchDTO matchDTO = new MatchDTO(
                match.getMap(),
                match.getGameMode(),
                match.getFragLimit(),
                match.getTimeLimit(),
                match.getTimeElapsed(),
                match.getScoreboardList().stream()
                        .map(scoreboard -> new ScoreboardDTO(
                                scoreboard.getPlayerName(),
                                scoreboard.getFrags(),
                                scoreboard.getKills(),
                                scoreboard.getDeaths()
                        ))
                        .toList()
        );
        return ResponseEntity.ok(matchDTO);
    }

    @GetMapping("/{endpoint}/stats")
    public ResponseEntity<StatsDTO> getStats(@PathVariable String endpoint) {
        if (serverService.IsExistsServer(endpoint)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serverService.getStats(endpoint));
    }

}
