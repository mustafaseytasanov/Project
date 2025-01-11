package com.example.project.service;

import com.example.project.dto.ServerDTO;
import com.example.project.dto.StatsDTO;
import com.example.project.model.Match;
import com.example.project.model.Server;
import com.example.project.repository.MatchRepository;
import com.example.project.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ServerService {
    private final ServerRepository serverRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public ServerService(ServerRepository serverRepository, MatchRepository matchRepository) {
        this.serverRepository = serverRepository;
        this.matchRepository = matchRepository;
    }

    public void saveOrUpdateInfo(String endpoint,
                                 ServerDTO serverDTO) {
        Server server = serverRepository.findByEndpoint(endpoint)
                .orElse(new Server(endpoint));
        server.setName(serverDTO.getName());
        server.setGameModes(serverDTO.getGameModes());
        serverRepository.save(server);
    }

    public Server getServer(String endpoint) {
        Optional<Server> server = serverRepository.findByEndpoint(endpoint);
        return server.orElse(null);
    }

    public List<Server> getAllServers() {
        return (List<Server>) serverRepository.findAll();
    }

    public boolean IsExistsServer(String endpoint) {
        return serverRepository.findByEndpoint(endpoint).isEmpty();
    }

    public StatsDTO getStats(String endpoint) {

        List<Match> matchList = matchRepository.findAllByServerEndpoint(endpoint);
        long totalMatchesPlayed = matchList.size();

        Map<LocalDate, Long> matchesPerDay = new HashMap<>();
        for (Match match : matchList) {
            LocalDate localDate = match.getTimeStamp().toLocalDate();
            if (!matchesPerDay.containsKey(localDate)) {
                matchesPerDay.put(localDate, 1L);
            } else {
                matchesPerDay.put(localDate, matchesPerDay.get(localDate) + 1);
            }
        }

        long maximumMatchesPerDay = matchesPerDay.values().stream().max(Long::compareTo).orElse(0L);
        double averageMatchesPerDay = matchesPerDay.values().stream().mapToLong(Long::longValue).average().orElse(0D);

        long maximumPopulation = matchList.stream()
                .mapToLong(match -> match.getScoreboardList().size())
                .max().orElse(0L);
        double averagePopulation = matchList.stream()
                .mapToLong(match -> match.getScoreboardList().size())
                .average().orElse(0D);

        Map<String, Long> gameModesMap = new HashMap<>();
        for (Match match : matchList) {
            String gameMode = match.getGameMode();
            if (!gameModesMap.containsKey(gameMode)) {
                gameModesMap.put(gameMode, 1L);
            } else {
                gameModesMap.put(gameMode, gameModesMap.get(gameMode) + 1);
            }
        }
        List<String> top5GameModes = getTop5Values(gameModesMap);

        Map<String, Long> gameMaps = new HashMap<>();
        for (Match match : matchList) {
            String map = match.getMap();
            if (!gameMaps.containsKey(map)) {
                gameMaps.put(map, 1L);
            } else {
                gameMaps.put(map, gameMaps.get(map) + 1);
            }
        }
        List<String> top5Maps = getTop5Values(gameMaps);

        return new StatsDTO(
                totalMatchesPlayed,
                maximumMatchesPerDay,
                averageMatchesPerDay,
                maximumPopulation,
                averagePopulation,
                top5GameModes,
                top5Maps
        );
    }

    private List<String> getTop5Values(Map<String, Long> inputMap) {
        List<String> resultList = new ArrayList<>();
        long maxValue;
        while (!inputMap.isEmpty() && (resultList.size() < 5)) {
            maxValue = Collections.max(inputMap.values());
            for (String map : inputMap.keySet()) {
                if (inputMap.get(map) == maxValue) {
                    resultList.add(map);
                    inputMap.remove(map);
                }
            }
        }
        return resultList;
    }
}
