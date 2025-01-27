package com.example.project.dto;

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

    public PlayerStatsDTO(long totalMatchesPlayed, long totalMatchesWon, String favoriteServer, long uniqueServers,
                          String favoriteGameMode, double averageScoreboardPercent, long maximumMatchesPerDay,
                          double averageMatchesPerDay, String lastMatchPlayed, double killToDeathRatio) {
        this.totalMatchesPlayed = totalMatchesPlayed;
        this.totalMatchesWon = totalMatchesWon;
        this.favoriteServer = favoriteServer;
        this.uniqueServers = uniqueServers;
        this.favoriteGameMode = favoriteGameMode;
        this.averageScoreboardPercent = averageScoreboardPercent;
        this.maximumMatchesPerDay = maximumMatchesPerDay;
        this.averageMatchesPerDay = averageMatchesPerDay;
        this.lastMatchPlayed = lastMatchPlayed;
        this.killToDeathRatio = killToDeathRatio;
    }

    public PlayerStatsDTO() {}

    public long getTotalMatchesPlayed() {
        return totalMatchesPlayed;
    }

    public long getTotalMatchesWon() {
        return totalMatchesWon;
    }

    public String getFavoriteServer() {
        return favoriteServer;
    }

    public long getUniqueServers() {
        return uniqueServers;
    }

    public String getFavoriteGameMode() {
        return favoriteGameMode;
    }

    public double getAverageScoreboardPercent() {
        return averageScoreboardPercent;
    }

    public long getMaximumMatchesPerDay() {
        return maximumMatchesPerDay;
    }

    public double getAverageMatchesPerDay() {
        return averageMatchesPerDay;
    }

    public String getLastMatchPlayed() {
        return lastMatchPlayed;
    }

    public double getKillToDeathRatio() {
        return killToDeathRatio;
    }

}
