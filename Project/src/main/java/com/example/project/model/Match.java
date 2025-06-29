package com.example.project.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class that represents table "matches" in database.
 * @author Mustafa
 * @version 1.0
 */
@Entity
@Table(name = "matches")
@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter
//@EqualsAndHashCode
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
}
