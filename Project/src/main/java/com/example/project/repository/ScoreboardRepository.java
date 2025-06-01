package com.example.project.repository;

import com.example.project.model.Scoreboard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface ScoreboardRepository
 * @author Mustafa
 * @version 1.0
 */
@Repository
public interface ScoreboardRepository extends CrudRepository<Scoreboard, Long> {
    List<Scoreboard> findByPlayerNameIgnoreCase(String name);
}
