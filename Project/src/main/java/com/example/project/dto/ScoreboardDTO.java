package com.example.project.dto;

import lombok.*;


/**
 * Class ScoreboardDTO
 * @author Mustafa
 * @version 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class ScoreboardDTO {
    private String name;
    private int frags;
    private int kills;
    private int deaths;
}
