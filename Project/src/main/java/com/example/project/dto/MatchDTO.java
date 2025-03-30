package com.example.project.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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
}
