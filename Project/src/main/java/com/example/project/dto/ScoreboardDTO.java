package com.example.project.dto;

import lombok.*;

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
