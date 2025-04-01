package com.example.project.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class that represents table "scoreboards" in database.
 * @author Mustafa
 * @version 1.0
 */
@Entity
@Table(name = "scoreboards")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Scoreboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;
    @Column(name = "player_name", nullable = false)
    private String playerName;
    @Column(nullable = false)
    private int frags;
    @Column(nullable = false)
    private int kills;
    @Column(nullable = false)
    private int deaths;

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setFrags(int frags) {
        this.frags = frags;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getFrags() {
        return frags;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public Match getMatch() {
        return match;
    }
}
