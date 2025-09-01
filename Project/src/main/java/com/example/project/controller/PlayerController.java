package com.example.project.controller;

import com.example.project.dto.PlayerStatsDTO;
import com.example.project.service.impl.PlayerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class for handling requests where path is "/players/...".
 * @author Mustafa
 * @version 1.0
 */
@Tag(name = "Players", description = "Information about players")
@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerServiceImpl playerService;

    public PlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @Operation(
            summary = "Getting stats about player",
            description = "It allows to get stats about player."
    )
    @GetMapping("/{name}/stats")
    public ResponseEntity<PlayerStatsDTO> getPlayerStats(
            @PathVariable @Parameter(description = "Name of player", required = true, example = "Player 1") String name) {
        PlayerStatsDTO playerStatsDTO = playerService.getPlayerStats(name);
        return new ResponseEntity<>(playerStatsDTO, HttpStatus.OK);
    }
}
