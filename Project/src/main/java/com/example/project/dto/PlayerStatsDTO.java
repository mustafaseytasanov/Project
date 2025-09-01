package com.example.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class PlayerStatsDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = "DTO for player stats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerStatsDTO {
    @Schema(description = "Total matches played", example = "100500")
    private long totalMatchesPlayed;
    @Schema(description = "Total matches won", example = "1000")
    private long totalMatchesWon;
    @Schema(description = "The server where the player appeared most often",
            example = "62.210.26.88-1337")
    private String favoriteServer;
    @Schema(description = "The number of unique servers the player has appeared on",
            example = "2")
    private long uniqueServers;
    @Schema(description = "The game mode the player played in the most matches",
            example = "DM")
    private String favoriteGameMode;
    @Schema(description = "Average value of every match scoreboardPercent " +
            "where scoreboardPercent = playersBelowCurrent / (totalPlayers - 1) * 100%",
            example = "76.145693")
    private double averageScoreboardPercent;
    @Schema(description = "Maximum matches per day", example = "25")
    private long maximumMatchesPerDay;
    @Schema(description = "Number of days from this player's first match to his " +
            "last match among all matches", example = "4.456240")
    private double averageMatchesPerDay;
    @Schema(description = "Date of last match", example = "2017-01-22T15:11:12Z")
    private String lastMatchPlayed;
    @Schema(description = "killToDeathRatio = totalKills / totalDeaths " +
            "where totalKills — sum of player's kills for all played matches, " +
            "totalDeaths — sum of player deaths for all played matches.",
            example = "3.124333")
    private double killToDeathRatio;
}
