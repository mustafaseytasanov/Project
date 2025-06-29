package com.example.project.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class that represents "scoreboards" table in database.
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
@Getter
@Setter
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

}
