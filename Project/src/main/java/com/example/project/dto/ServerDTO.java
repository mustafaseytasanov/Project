package com.example.project.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServerDTO {
    private String name;
    private List<String> gameModes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<String> gameModes) {
        this.gameModes = gameModes;
    }

}
