package com.example.project.dto;

import lombok.*;

import java.util.List;

/**
 * Class MatchDTO
 * @author Mustafa
 * @version 1.0
 */
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
