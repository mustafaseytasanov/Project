package com.example.project.repository;

import com.example.project.model.Scoreboard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreboardRepository extends CrudRepository<Scoreboard, Long> {
    List<Scoreboard> findByPlayerNameIgnoreCase(String name);
}
