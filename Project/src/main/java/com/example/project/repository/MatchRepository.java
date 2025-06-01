package com.example.project.repository;

import com.example.project.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface MatchRepository
 * @author Mustafa
 * @version 1.0
 */
@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    Match findByServerEndpointAndTimeStamp(String serverEndpoint, LocalDateTime timeStamp);

    List<Match> findAllByServerEndpoint(String serverEndpoint);

    @Query("SELECT m FROM Match m ORDER BY m.timeStamp DESC")
    List<Match> findAllDesc();
}
