package com.example.project.controller;

import com.example.project.dto.PlayerStatsDTO;
import com.example.project.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{name}/stats")
    public ResponseEntity<PlayerStatsDTO> getPlayerStats(@PathVariable String name) {
        PlayerStatsDTO playerStatsDTO = playerService.getPlayerStats(name);
        return new ResponseEntity<>(playerStatsDTO, HttpStatus.OK);
    }
}
