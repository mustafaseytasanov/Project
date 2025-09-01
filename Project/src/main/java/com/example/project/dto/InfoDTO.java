package com.example.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Class InfoDTO
 * @author Mustafa
 * @version 1.0
 */
@Schema(description = "Information about server")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoDTO {
    @Schema(description = "Name of server", example = "] My Perfect Server [")
    @NotBlank
    private String name;
    @Schema(description = "Game modes of server", example = "[ \"DM\", \"TDM\" ]")
    private List<String> gameModes;
}
