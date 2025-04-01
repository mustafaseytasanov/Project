package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class PlayerStatsDTO
 * @author Mustafa
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlayerStatsDTO {
    private long totalMatchesPlayed;
    private long totalMatchesWon;
    private String favoriteServer;
    private long uniqueServers;
    private String favoriteGameMode;
    private double averageScoreboardPercent;
    private long maximumMatchesPerDay;
    private double averageMatchesPerDay;
    private String lastMatchPlayed;
    private double killToDeathRatio;

}
