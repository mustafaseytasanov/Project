package com.example.project.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@ToString
public class MatchDTO {
    private String map;
    private String gameMode;
    private int fragLimit;
    private int timeLimit;
    private double timeElapsed;
    private List<ScoreboardDTO> scoreboard;

    public MatchDTO(String map, String gameMode, int fragLimit, int timeLimit, double timeElapsed, List<ScoreboardDTO> scoreboard) {
        this.map = map;
        this.gameMode = gameMode;
        this.fragLimit = fragLimit;
        this.timeLimit = timeLimit;
        this.timeElapsed = timeElapsed;
        this.scoreboard = scoreboard;
    }

    public String getMap() {
        return map;
    }

    public String getGameMode() {
        return gameMode;
    }

    public int getFragLimit() {
        return fragLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }
    public double getTimeElapsed() {
        return timeElapsed;
    }

    public List<ScoreboardDTO> getScoreboard() {
        return scoreboard;
    }

}
