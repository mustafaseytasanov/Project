package com.example.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Class StatsDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = "DTO for server stats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatsDTO {
    @Schema(description = "Total matches played", example = "100500")
    private long totalMatchesPlayed;
    @Schema(description = "Maximum matches per day", example = "33")
    private long maximumMatchesPerDay;
    @Schema(description = "Average matches per day", example = "24.456240")
    private double averageMatchesPerDay;
    @Schema(description = "Maximum population", example = "32")
    private long maximumPopulation;
    @Schema(description = "Average population", example = "20.450000")
    private double averagePopulation;
    @Schema(description = "Top 5 game modes", example = "[ \"DM\", \"TDM\" ]")
    private List<String> top5GameModes;
    @Schema(description = "Top 5 maps", example = "[ \"DM-HelloWorld\", " +
            "\"DM-1on1-Rose\", \"DM-Kitchen\", \"DM-Camper Paradise\", \"DM-Appalachian Wonderland\"]")
    private List<String> top5Maps;
}
