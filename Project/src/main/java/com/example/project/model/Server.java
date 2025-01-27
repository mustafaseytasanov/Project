package com.example.project.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servers")
@AllArgsConstructor
@EqualsAndHashCode
public class Server {

    @Id
    @Column(nullable = false)
    private String endpoint;
    @Column(nullable = false)
    private String name;
    @ElementCollection
    @CollectionTable(name = "server_game_modes", joinColumns = @JoinColumn(name = "server_endpoint"))
    @Column(name = "game_mode")
    private List<String> gameModes = new ArrayList<>();
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matches = new ArrayList<>();

    public Server(String endpoint) {
        this.endpoint = endpoint;
    }

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

    public String getEndpoint() {
        return endpoint;
    }

    public Server() {}
}
