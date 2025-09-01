package com.example.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class PopularServerDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = "DTO for Popular Server")
@AllArgsConstructor
@Getter
@Setter
public class PopularServerDTO {
    @Schema(description = "Server endpoint", example = "62.210.26.88-1337")
    private String endpoint;
    @Schema(description = "Server name", example = ">> Sniper Heaven <<")
    private String name;
    @Schema(description = "Average amount of matches per day", example = "24.456240")
    private double averageMatchesPerDay;
}
