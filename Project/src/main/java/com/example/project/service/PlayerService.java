package com.example.project.service;

import com.example.project.dto.BestPlayerDTO;
import com.example.project.dto.PlayerStatsDTO;

import java.util.List;

/**
 * Interface PlayerService
 * @author Mustafa
 * @version 1.0
 */
public interface PlayerService {
    PlayerStatsDTO getPlayerStats(String name);
    List<BestPlayerDTO> getBestPlayers(int bestPlayersCount);

}
