package com.example.project.service.impl;

import com.example.project.dto.InfoDTO;
import com.example.project.dto.PopularServerDTO;
import com.example.project.dto.ServerDTO;
import com.example.project.dto.StatsDTO;
import com.example.project.model.Match;
import com.example.project.model.Server;
import com.example.project.repository.MatchRepository;
import com.example.project.repository.ServerRepository;
import com.example.project.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Implementation of ServerService interface
 * @author Mustafa
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;
    private final MatchRepository matchRepository;


    public void saveOrUpdateInfo(String endpoint,
                                 InfoDTO infoDTO) {
        Server server = serverRepository.findByEndpoint(endpoint)
                .orElse(new Server(endpoint));
        server.setName(infoDTO.getName());
        server.setGameModes(infoDTO.getGameModes());
        serverRepository.save(server);
    }

    public InfoDTO getOneServerInfo(String endpoint) {
        Optional<Server> server = serverRepository.findByEndpoint(endpoint);
        return server.map(value -> new InfoDTO(value.getName(), value.getGameModes())).orElse(null);
    }

    public List<ServerDTO> getAllServersInfo() {
        List<Server> serverList = (List<Server>) serverRepository.findAll();
        List<ServerDTO> serverDTOList = new ArrayList<>();
        for (Server server : serverList) {
            ServerDTO serverDTO = new ServerDTO();
            serverDTO.setEndpoint(server.getEndpoint());
            InfoDTO infoDTO = new InfoDTO(server.getName(), server.getGameModes());
            serverDTO.setInfo(infoDTO);
            serverDTOList.add(serverDTO);
        }
        return serverDTOList;
    }

    public boolean isEmptyServer(String endpoint) {
        return serverRepository.findByEndpoint(endpoint).isEmpty();
    }

    public StatsDTO getStats(String endpoint) {

        List<Match> matchList = matchRepository.findAllByServerEndpoint(endpoint);
        long totalMatchesPlayed = matchList.size();

        Map<LocalDate, Long> matchesPerDay = getMatchesPerDayMap(matchList);
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

    private Map<LocalDate, Long> getMatchesPerDayMap(List<Match> matchList) {
        Map<LocalDate, Long> matchesPerDay = new HashMap<>();
        for (Match match : matchList) {
            LocalDate localDate = match.getTimeStamp().toLocalDate();
            if (!matchesPerDay.containsKey(localDate)) {
                matchesPerDay.put(localDate, 1L);
            } else {
                matchesPerDay.put(localDate, matchesPerDay.get(localDate) + 1);
            }
        }
        return matchesPerDay;
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

    public List<PopularServerDTO> getPopularServers(int popularServersCount) {
        List<Server> serverList = (List<Server>) serverRepository.findAll();
        Set<Server> serverSet = new HashSet<>(serverList);
        List<PopularServerDTO> popularServerDTOs = new ArrayList<>();
        for (Server server : serverSet) {
            List<Match> matchList = matchRepository.findAllByServerEndpoint(server.getEndpoint());
            Map<LocalDate, Long> matchesPerDay = getMatchesPerDayMap(matchList);
            double averageMatchesPerDay = matchesPerDay.values().stream().mapToLong(Long::longValue).average().orElse(0D);
            popularServerDTOs.add(new PopularServerDTO(server.getEndpoint(),
                    server.getName(), averageMatchesPerDay));
        }
        popularServerDTOs.sort((o1, o2) -> {
            double value1 = o1.getAverageMatchesPerDay();
            double value2 = o2.getAverageMatchesPerDay();
            return Double.compare(value2, value1);
        });
        if (popularServersCount < popularServerDTOs.size()) {
            popularServerDTOs = popularServerDTOs.subList(0, popularServersCount);
        }
        return popularServerDTOs;
    }
}
