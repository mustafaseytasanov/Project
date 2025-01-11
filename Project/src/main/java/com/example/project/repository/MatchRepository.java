package com.example.project.repository;

import com.example.project.model.Match;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {
    Match findByServerEndpointAndTimeStamp(String server_endpoint, LocalDateTime timeStamp);

    List<Match> findAllByServerEndpoint(String serverEndpoint);
}
