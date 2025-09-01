package com.example.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class ReportDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = "DTO for report of recent matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    @Schema(description = "Server endpoint", example = "62.210.26.88-1337")
    private String server;
    @Schema(description = "TimeStamp", example = "2017-01-22T15:11:12Z")
    private String timeStamp;
    @Schema(description = "Results")
    private MatchDTO results;

}
