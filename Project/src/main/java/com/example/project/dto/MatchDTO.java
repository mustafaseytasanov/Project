package com.example.project.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
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

}
