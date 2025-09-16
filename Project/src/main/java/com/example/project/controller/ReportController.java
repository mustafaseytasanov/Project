package com.example.project.controller;

import com.example.project.dto.BestPlayerDTO;
import com.example.project.dto.PopularServerDTO;
import com.example.project.dto.ReportDTO;
import com.example.project.service.impl.MatchServiceImpl;
import com.example.project.service.impl.PlayerServiceImpl;
import com.example.project.service.impl.ServerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


/**
 * Class for handling requests where path is "/reports/...".
 * @author Mustafa
 * @version 1.1
 */
@Tag(name = "Reports", description = "Reports about matches, players and servers")
@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final MatchServiceImpl matchService;
    private final PlayerServiceImpl playerService;
    private final ServerServiceImpl serverService;

    @Operation(
            summary = "Getting reports about recent matches",
            description = "It allows to get reports about recent matches " +
                    "in descending order: first, matches with the largest timestamp."
    )
    @GetMapping("/recent-matches")
    public ResponseEntity<List<ReportDTO>> getRecentMatches(
            @RequestParam(value = "count", required = false, defaultValue = "5")
            @Parameter(description = "The number of records to include in the report",
                    example = "5") int count) {
        count = Math.min(count, 50);
        if (count <= 0) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(matchService.getRecentMatches(count));
    }

    @Operation(
            summary = "Getting reports about best players",
            description = "It allows to get reports about best players " +
                    "in descending order of 'killToDeathRatio' parameter."
    )
    @GetMapping("/best-players")
    public ResponseEntity<List<BestPlayerDTO>> getBestPlayers(
            @RequestParam(value = "count", required = false, defaultValue = "5")
            @Parameter(description = "The number of records to include in the report",
                    example = "5") int count) {
        count = Math.min(count, 50);
        if (count <= 0) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(playerService.getBestPlayers(count));
    }

    @Operation(
            summary = "Getting reports about popular servers",
            description = "It allows to get reports about popular servers " +
                    "in descending order of 'averageMatchesPerDay' parameter."
    )
    @GetMapping("/popular-servers")
    public ResponseEntity<List<PopularServerDTO>> getPopularServers(
            @RequestParam(value = "count", required = false, defaultValue = "5")
            @Parameter(description = "The number of records to include in the report",
                    example = "5") int count) {
        count = Math.min(count, 50);
        if (count <= 0) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(serverService.getPopularServers(count));
    }

}
