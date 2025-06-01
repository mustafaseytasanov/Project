package com.example.project.controller;

import com.example.project.dto.*;
import com.example.project.model.Match;
import com.example.project.service.impl.MatchServiceImpl;
import com.example.project.service.impl.ServerServiceImpl;
import com.example.project.exception.ServerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Class for handling requests where path is "/servers/...".
 * @author Mustafa
 * @version 1.1
 */
@RestController
@RequestMapping("/servers")
@AllArgsConstructor
public class ServerController {

    private final ServerServiceImpl serverService;
    private final MatchServiceImpl matchService;

    @PutMapping("/{endpoint}/info")
    public ResponseEntity<Void> putInfo(@PathVariable String endpoint,
                                     @RequestBody InfoDTO infoDTO) { // DTO
        serverService.saveOrUpdateInfo(endpoint, infoDTO);
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
    public ResponseEntity<InfoDTO> getOneServerInfo(@PathVariable String endpoint) {
        InfoDTO serverInfo = serverService.getOneServerInfo(endpoint);
        if (serverInfo != null) {
            return ResponseEntity.ok(serverInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/info")
    public ResponseEntity<List<ServerDTO>> getAllServersInfo() {
        return ResponseEntity.ok(serverService.getAllServersInfo());
    }

    @GetMapping("/{endpoint}/matches/{timestamp}")
    public ResponseEntity<MatchDTO> getMatch(
            @PathVariable String endpoint,
            @PathVariable String timestamp) {

        if (serverService.isEmptyServer(endpoint)) {
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
        if (serverService.isEmptyServer(endpoint)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serverService.getStats(endpoint));
    }

}
