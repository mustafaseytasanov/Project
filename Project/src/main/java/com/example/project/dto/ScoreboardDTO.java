package com.example.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


/**
 * Class ScoreboardDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = "DTO for Scoreboard")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class ScoreboardDTO {
    @Schema(description = "Name of player", example = "Player1")
    private String name;
    @Schema(description = "Frags", example = "20")
    private int frags;
    @Schema(description = "Kills", example = "21")
    private int kills;
    @Schema(description = "Deaths", example = "3")
    private int deaths;
}
