package com.example.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class BestPlayerDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = " DTO for Best Player")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BestPlayerDTO {
    @Schema(description = "Player name", example = "must99")
    private String name;
    @Schema(description = "killToDeathRatio = totalKills / totalDeaths",
            example = "3.124333")
    private double killToDeathRatio;
}
