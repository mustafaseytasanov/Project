package com.example.project.dto;

import java.util.List;

public class StatsDTO {
    private long totalMatchesPlayed;
    private long maximumMatchesPerDay;
    private double averageMatchesPerDay;
    private long maximumPopulation;
    private double averagePopulation;
    private List<String> top5GameModes;
    private List<String> top5Maps;

    public StatsDTO(long totalMatchesPlayed, long maximumMatchesPerDay, double averageMatchesPerDay, long maximumPopulation, double averagePopulation, List<String> top5GameModes, List<String> top5Maps) {
        this.totalMatchesPlayed = totalMatchesPlayed;
        this.maximumMatchesPerDay = maximumMatchesPerDay;
        this.averageMatchesPerDay = averageMatchesPerDay;
        this.maximumPopulation = maximumPopulation;
        this.averagePopulation = averagePopulation;
        this.top5GameModes = top5GameModes;
        this.top5Maps = top5Maps;
    }

    public long getTotalMatchesPlayed() {
        return totalMatchesPlayed;
    }

    public long getMaximumMatchesPerDay() {
        return maximumMatchesPerDay;
    }

    public double getAverageMatchesPerDay() {
        return averageMatchesPerDay;
    }

    public long getMaximumPopulation() {
        return maximumPopulation;
    }

    public double getAveragePopulation() {
        return averagePopulation;
    }

    public List<String> getTop5GameModes() {
        return top5GameModes;
    }

    public List<String> getTop5Maps() {
        return top5Maps;
    }
}
