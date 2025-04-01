package com.example.project.service.impl;

import com.example.project.dto.MatchDTO;
import com.example.project.dto.ReportDTO;
import com.example.project.dto.ScoreboardDTO;
import com.example.project.exception.ServerNotFoundException;
import com.example.project.model.Match;
import com.example.project.model.Scoreboard;
import com.example.project.model.Server;
import com.example.project.repository.MatchRepository;
import com.example.project.repository.ServerRepository;
import com.example.project.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of MatchService interface
 * @author Mustafa
 * @version 1.0
 */
@Service
public class MatchServiceImpl implements MatchService {

    private final ServerRepository serverRepository;
    private final MatchRepository matchRepository;
    @Autowired
    public MatchServiceImpl(ServerRepository serverRepository, MatchRepository matchRepository) {
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

        String gameMode = matchDTO.getGameMode();
        List<String> gameModes = server.getGameModes();
        if (!gameModes.contains(gameMode)) {
            gameModes.add(gameMode);
            server.setGameModes(gameModes);
        }
        serverRepository.save(server);
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


    public List<ReportDTO> getRecentMatches(int recentMatchesCount) {
        List<Match> allMatchesDesc = matchRepository.findAllDesc();
        List<Match> recentMatches;
        if (recentMatchesCount >= allMatchesDesc.size()) {
            recentMatches = allMatchesDesc;
        } else {
            recentMatches = allMatchesDesc.subList(0, recentMatchesCount);
        }

        List<ReportDTO> reportDTOs = new ArrayList<>();
        for (Match match : recentMatches) {
            MatchDTO results = new MatchDTO(match.getMap(),
                    match.getGameMode(), match.getFragLimit(),
                    match.getTimeLimit(), match.getTimeElapsed(),
                    match.getScoreboardList().stream()
                            .map(scoreboard -> new ScoreboardDTO(scoreboard.getPlayerName(),
                                    scoreboard.getFrags(), scoreboard.getKills(), scoreboard.getDeaths()))
                            .toList());
            reportDTOs.add(new ReportDTO(match.getServer().getEndpoint(),
                    match.getTimeStamp().toString() + "Z", results));
        }

        return reportDTOs;
    }


    private LocalDateTime parse(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.of("UTC"));
        return LocalDateTime.parse(timestamp, formatter);
    }




}
