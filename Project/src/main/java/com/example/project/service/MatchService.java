package com.example.project.service;

import com.example.project.dto.MatchDTO;
import com.example.project.exception.ServerNotFoundException;
import com.example.project.model.Match;
import com.example.project.model.Scoreboard;
import com.example.project.model.Server;
import com.example.project.repository.MatchRepository;
import com.example.project.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MatchService {

    private final ServerRepository serverRepository;
    private final MatchRepository matchRepository;
    @Autowired
    public MatchService(ServerRepository serverRepository, MatchRepository matchRepository) {
        this.serverRepository = serverRepository;
        this.matchRepository = matchRepository;
    }


    public void addMatch(String endpoint, String timeStamp, MatchDTO matchDTO) {
        Server server = serverRepository.findById(endpoint)
                .orElseThrow(() -> new ServerNotFoundException("Server not found"));

        Match match = new Match();
        match.setServer(server);
        match.setTimeStamp(parse(timeStamp));
        match.setMap(matchDTO.getMap());
        match.setGameMode(matchDTO.getGameMode());
        match.setFragLimit(matchDTO.getFragLimit());
        match.setTimeLimit(matchDTO.getTimeLimit());
        match.setTimeElapsed(matchDTO.getTimeElapsed());

        List<Scoreboard> scoreboards = matchDTO.getScoreboard().stream()
                .map(scoreboardDTO -> {
                    Scoreboard scoreboard = new Scoreboard();
                    scoreboard.setMatch(match);
                    scoreboard.setPlayerName(scoreboardDTO.getName());
                    scoreboard.setFrags(scoreboardDTO.getFrags());
                    scoreboard.setKills(scoreboardDTO.getKills());
                    scoreboard.setDeaths(scoreboardDTO.getDeaths());
                    return scoreboard;
                })
                .toList();

        match.setScoreboardList(scoreboards);
        matchRepository.save(match);
    }

    public Match getMatch(String endpoint, String timeStamp) {
        return matchRepository.findByServerEndpointAndTimeStamp(endpoint, parse(timeStamp));
    }

    private LocalDateTime parse(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.of("UTC"));
        return LocalDateTime.parse(timestamp, formatter);
    }




}
