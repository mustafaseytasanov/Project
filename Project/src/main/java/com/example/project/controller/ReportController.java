package com.example.project.controller;

import com.example.project.dto.BestPlayerDTO;
import com.example.project.dto.PopularServerDTO;
import com.example.project.dto.ReportDTO;
import com.example.project.service.impl.MatchServiceImpl;
import com.example.project.service.impl.PlayerServiceImpl;
import com.example.project.service.impl.ServerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Class for handling requests where path is "/reports/...".
 * @author Mustafa
 * @version 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final MatchServiceImpl matchService;
    private final PlayerServiceImpl playerService;
    private final ServerServiceImpl serverService;

    @GetMapping("/recent-matches/{count}")
    public ResponseEntity<List<ReportDTO>> getRecentMatches(@PathVariable(required = false) Optional<Integer> count) {
        int recentMatchesCount = getIntegerValue(count);
        if (recentMatchesCount <= 0) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(matchService.getRecentMatches(recentMatchesCount));
    }

    @GetMapping("/best-players/{count}")
    public ResponseEntity<List<BestPlayerDTO>> getBestPlayers(@PathVariable(required = false) Optional<Integer> count) {
        int bestPlayersCount = getIntegerValue(count);
        if (bestPlayersCount <= 0) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(playerService.getBestPlayers(bestPlayersCount));
    }

    @GetMapping("/popular-servers/{count}")
    public ResponseEntity<List<PopularServerDTO>> getPopularServers(
            @PathVariable(required = false) Optional<Integer> count) {
        int popularServersCount = getIntegerValue(count);
        if (popularServersCount <= 0) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(serverService.getPopularServers(popularServersCount));
    }

    private int getIntegerValue(Optional<Integer> count) {
        int intValueCount = count.orElse(5);
        if (intValueCount >= 50) {
            intValueCount = 50;
        }
        return intValueCount;
    }

}
