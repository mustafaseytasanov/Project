package com.example.project.dto;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatsDTO {
    private long totalMatchesPlayed;
    private long maximumMatchesPerDay;
    private double averageMatchesPerDay;
    private long maximumPopulation;
    private double averagePopulation;
    private List<String> top5GameModes;
    private List<String> top5Maps;
}
