package com.example.project.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * Class ServerDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = "DTO for Server")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ServerDTO {
    @Schema(description = "Server endpoint", example = "62.210.26.88-1337")
    private String endpoint;
    @Schema(description = "Information about server")
    private InfoDTO info;
}
