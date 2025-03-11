package com.example.project.controller;

import com.example.project.dto.ReportDTO;
import com.example.project.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final MatchService matchService;

    @GetMapping("/recent-matches/{count}")
    public ResponseEntity<List<ReportDTO>> getRecentMatches(@PathVariable(required = false) Optional<Integer> count) {

        int recentMatchesCount = count.orElse(5);

        if (recentMatchesCount <= 0) {
            return ResponseEntity.ok(Collections.emptyList());
        } else if (recentMatchesCount >= 50) {
            recentMatchesCount = 50;
        }

        return ResponseEntity.ok(matchService.getRecentMatches(recentMatchesCount));

    }
}
