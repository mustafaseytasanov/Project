package com.example.project.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class that represents table "servers" in database.
 * @author Mustafa
 * @version 1.0
 */
@Entity
@Table(name = "servers")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
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
}
