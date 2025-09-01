package com.example.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

/**
 * Class MatchDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = "DTO for Match")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MatchDTO {
    @Schema(description = "Map name", example = "DM-HelloWorld")
    @NotBlank
    private String map;
    @Schema(description = "Game mode of match", example = "DM")
    @NotBlank
    private String gameMode;
    @Schema(description = "Frag limit", example = "20")
    @Min(0)
    private int fragLimit;
    @Schema(description = "Time limit", example = "20")
    @Min(0)
    private int timeLimit;
    @Schema(description = "Time elapsed", example = "12.345678")
    private double timeElapsed;
    @Schema(description = "Results of players")
    private List<ScoreboardDTO> scoreboard;
}
