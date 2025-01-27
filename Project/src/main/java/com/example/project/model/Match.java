package com.example.project.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timeStamp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_endpoint", nullable = false)
    private Server server;
    @Column(nullable = false)
    private String map;
    @Column(name = "game_mode", nullable = false)
    private String gameMode;
    @Column(name = "frag_limit", nullable = false)
    private int fragLimit;
    @Column(name = "time_limit", nullable = false)
    private int timeLimit;
    @Column(name = "time_elapsed", nullable = false)
    private double timeElapsed;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scoreboard> scoreboardList = new ArrayList<>();

     public void setServer(Server server) {
         this.server = server;
     }

     public void setTimeStamp(LocalDateTime timeStamp) {
         this.timeStamp = timeStamp;
     }

    public void setMap(String map) {
         this.map = map;
    }

    public void setGameMode(String gameMode) {
         this.gameMode = gameMode;
    }

    public void setFragLimit(int fragLimit) {
         this.fragLimit = fragLimit;
    }

    public void setTimeLimit(int timeLimit) {
         this.timeLimit = timeLimit;
    }

    public void setTimeElapsed(double timeElapsed) {
         this.timeElapsed = timeElapsed;
    }

    public void setScoreboardList(List<Scoreboard> scoreboardList) {
         this.scoreboardList = scoreboardList;
    }

    public List<Scoreboard> getScoreboardList() {
         return scoreboardList;
    }

    public String getMap() {
         return map;
    }

    public String getGameMode() {
         return gameMode;
    }

    public int getFragLimit() {
         return fragLimit;
    }

    public int getTimeLimit() {
         return timeLimit;
    }

    public double getTimeElapsed() {
         return timeElapsed;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Server getServer() {
         return server;
    }
}
