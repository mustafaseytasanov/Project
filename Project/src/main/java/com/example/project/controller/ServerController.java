package com.example.project.controller;

import com.example.project.dto.*;
import com.example.project.model.Match;
import com.example.project.service.impl.MatchServiceImpl;
import com.example.project.service.impl.ServerServiceImpl;
import com.example.project.exception.ServerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Class for handling requests where path is "/servers/...".
 * @author Mustafa
 * @version 1.1
 */
@Validated
@Tag(name = "Servers", description = "Interaction with servers")
@RestController
@RequestMapping("/servers")
@AllArgsConstructor
public class ServerController {

    private final ServerServiceImpl serverService;
    private final MatchServiceImpl matchService;

    @Operation(
            summary = "advertise-request",
            description = "It allows to add new server"
    )
    @ApiResponse(responseCode = "400", description = "Validation error in request body")
    @ApiResponse(responseCode = "500", description = "Validation error in \"endpoint\" parameter")
    @PutMapping("/{endpoint}/info")
    public ResponseEntity<Void> putInfo(
            @PathVariable
            @Length(min = 6)
            @Parameter(description = "Server endpoint",
                    required = true, example = "167.42.23.32-1337") String endpoint,
            @Valid @RequestBody InfoDTO infoDTO) {
        serverService.saveOrUpdateInfo(endpoint, infoDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Saving of match for this server",
            description = "It allows to save match for existing server. " +
                    "If server doesn't exist then return 400 Bad Request."
    )
    @ApiResponse(responseCode = "400", description = "Validation error in request body")
    @ApiResponse(responseCode = "500", description = "Validation error in \"endpoint\" or \"timestamp\" parameters")
    @PutMapping("/{endpoint}/matches/{timestamp}")
    public ResponseEntity<Void> addMatch(
            @PathVariable
            @Length(min = 6)
            @Parameter(description = "Server endpoint", required = true, example = "167.42.23.32-1337") String endpoint,
            @PathVariable
            @Length(min = 12)
            @Parameter(description = "Match end time in UTC", required = true, example = "2017-01-22T15:17:00Z") String timestamp,
            @Valid @RequestBody MatchDTO matchDTO) {
        try {
            matchService.addMatch(endpoint, timestamp, matchDTO);
            return ResponseEntity.ok().build();
        } catch (ServerNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @Operation(
            summary = "Getting info about server",
            description = "It allows to get information about server. " +
                    "If server doesn't exist then return 404 NotFound."
    )
    @GetMapping("/{endpoint}/info")
    public ResponseEntity<InfoDTO> getOneServerInfo(
            @PathVariable @Parameter(description = "Server endpoint", required = true, example = "167.42.23.32-1337") String endpoint) {
        InfoDTO serverInfo = serverService.getOneServerInfo(endpoint);
        if (serverInfo != null) {
            return ResponseEntity.ok(serverInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Getting info about servers",
            description = "It allows to get information about all servers. "
    )
    @GetMapping("/info")
    public ResponseEntity<List<ServerDTO>> getAllServersInfo() {
        return ResponseEntity.ok(serverService.getAllServersInfo());
    }

    @Operation(
            summary = "Getting information about match",
            description = "It allows to get information about match. " +
                    "If there was no PUT request to this address, an empty " +
                    "response with the code 404 Not Found should be returned."
    )
    @GetMapping("/{endpoint}/matches/{timestamp}")
    public ResponseEntity<MatchDTO> getMatch(
            @PathVariable @Parameter(description = "Server endpoint", required = true, example = "167.42.23.32-1337") String endpoint,
            @PathVariable @Parameter(description = "Match end time in UTC", required = true, example = "2017-01-22T15:17:00Z") String timestamp) {

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

    @Operation(
            summary = "Getting stats about server",
            description = "It allows to get stats about server. " +
                    "If server doesn't exist return a code 404 Not Found."
    )
    @GetMapping("/{endpoint}/stats")
    public ResponseEntity<StatsDTO> getStats(
            @PathVariable @Parameter(description = "Server endpoint", required = true, example = "167.42.23.32-1337") String endpoint) {
        if (serverService.isEmptyServer(endpoint)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serverService.getStats(endpoint));
    }

}
