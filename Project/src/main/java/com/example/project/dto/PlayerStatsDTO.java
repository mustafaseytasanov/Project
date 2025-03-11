package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
