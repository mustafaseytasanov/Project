package com.example.project.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class ScoreboardDTO {
    private String name;
    private int frags;
    private int kills;
    private int deaths;

    public ScoreboardDTO(String name, int frags, int kills, int deaths) {
        this.name = name;
        this.frags = frags;
        this.kills = kills;
        this.deaths = deaths;
    }

}
