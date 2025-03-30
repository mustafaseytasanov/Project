package com.example.project.service;


import com.example.project.dto.BestPlayerDTO;
import com.example.project.dto.PlayerStatsDTO;
import com.example.project.model.Scoreboard;
import com.example.project.repository.ScoreboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class PlayerService {

    private final ScoreboardRepository scoreboardRepository;

    @Autowired
    public PlayerService(ScoreboardRepository scoreboardRepository) {
        this.scoreboardRepository = scoreboardRepository;
    }

    public PlayerStatsDTO getPlayerStats(String name) {
        name = name.toLowerCase();
        List<Scoreboard> playerScoreboards = scoreboardRepository.findByPlayerNameIgnoreCase(name);
        if (playerScoreboards.isEmpty()) {
            return new PlayerStatsDTO();
        }

        long totalMatchesPlayed = playerScoreboards.size();
        long totalMatchesWon = 0;
        for (Scoreboard scoreboard : playerScoreboards) {
            String winnerName = scoreboard.getMatch().getScoreboardList().getFirst().getPlayerName().toLowerCase();
            if (winnerName.equals(name)) {
                totalMatchesWon++;
            }
        }

        Map<String, Long> serversCount = new HashMap<>();
        for (Scoreboard scoreboard : playerScoreboards) {
            String endpoint = scoreboard.getMatch().getServer().getEndpoint();
            if (serversCount.containsKey(endpoint)) {
                serversCount.put(endpoint, serversCount.get(endpoint) + 1);
            }
            serversCount.put(endpoint, 1L);
        }
        String favoriteServer = serversCount.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();

        long uniqueServers = serversCount.size();

        String favoriteGameMode;
        Map<String, Long> gameModeCount = new HashMap<>();
        for (Scoreboard scoreboard : playerScoreboards) {
            String gameMode = scoreboard.getMatch().getGameMode();
            if (gameModeCount.containsKey(gameMode)) {
                gameModeCount.put(gameMode, gameModeCount.get(gameMode) + 1);
            }
            gameModeCount.put(gameMode, 1L);
        }
        favoriteGameMode = gameModeCount.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();

        double totalScoreboards = 0;
        for (Scoreboard scoreboard : playerScoreboards) {
            List<Scoreboard> scoreboardList = scoreboard.getMatch().getScoreboardList();
            if (scoreboardList.size() == 1) {
                totalScoreboards++;
                continue;
            }
            long playersBelowCurrent = 0;
            for (int i = 0; i < scoreboardList.size(); i++) {
                Scoreboard scoreboard1 = scoreboardList.get(i);
                if (scoreboard1.getPlayerName().equalsIgnoreCase(name)) {
                    playersBelowCurrent = scoreboardList.size() - i - 1;
                    break;
                }
            }
            totalScoreboards += (double) playersBelowCurrent / (scoreboardList.size() - 1) * 100.0;
        }
        double averageScoreboardPercent = totalScoreboards / playerScoreboards.size() * 100;

        Map<LocalDate, Long> matchesPerDay = new HashMap<>();
        for (Scoreboard scoreboard: playerScoreboards) {
            LocalDate localDate = scoreboard.getMatch().getTimeStamp().toLocalDate();
            if (!matchesPerDay.containsKey(localDate)) {
                matchesPerDay.put(localDate, 0L);
            }
            matchesPerDay.put(localDate, matchesPerDay.get(localDate) + 1);
        }
        long maximumMatchesPerDay = matchesPerDay.values().stream().mapToLong(Long::longValue).max().getAsLong();

        double averageMatchesPerDay = (double) matchesPerDay.values().stream().mapToLong(Long::longValue).sum()
                / matchesPerDay.size();

        List<LocalDateTime> matchesTime = new ArrayList<>();
        for (Scoreboard scoreboard : playerScoreboards) {
            matchesTime.add(scoreboard.getMatch().getTimeStamp());
        }
        LocalDateTime lastMatch = matchesTime.stream().max(Comparator.naturalOrder()).orElse(null);
        String lastMatchPlayed = lastMatch.toLocalDate() + "T" + lastMatch.toLocalTime() + "Z";

        long kills = playerScoreboards.stream().mapToLong(Scoreboard::getKills).sum();
        long deaths = playerScoreboards.stream().mapToLong(Scoreboard::getDeaths).sum();
        if (deaths == 0) {
            deaths = 1;
        }
        double killToDeathRatio = (double) kills / (double) deaths;
        return new PlayerStatsDTO(
                totalMatchesPlayed,
                totalMatchesWon,
                favoriteServer,
                uniqueServers,
                favoriteGameMode,
                averageScoreboardPercent,
                maximumMatchesPerDay,
                averageMatchesPerDay,
                lastMatchPlayed,
                killToDeathRatio);
    }

    public List<BestPlayerDTO> getBestPlayers(int bestPlayersCount) {
        List<Scoreboard> scoreboardList = (List<Scoreboard>) scoreboardRepository.findAll();
        Map<String, int[]> playerKillsAndDeathsMap = new HashMap<>();
        int[] killsAndDeathsArray;
        for (Scoreboard scoreboard : scoreboardList) {
            String playerName = scoreboard.getPlayerName();
            if (!playerKillsAndDeathsMap.containsKey(playerName)) {
                killsAndDeathsArray = new int[3];
                killsAndDeathsArray[0] = 1;
                killsAndDeathsArray[1] = scoreboard.getKills();
                killsAndDeathsArray[2] = scoreboard.getDeaths();
                playerKillsAndDeathsMap.put(playerName, killsAndDeathsArray);
            } else {
                killsAndDeathsArray = playerKillsAndDeathsMap.get(playerName);
                killsAndDeathsArray[0] += 1;
                killsAndDeathsArray[1] += scoreboard.getKills();
                killsAndDeathsArray[2] += scoreboard.getDeaths();
                playerKillsAndDeathsMap.put(playerName, killsAndDeathsArray);
            }
        }
        List<BestPlayerDTO> bestPlayers = new ArrayList<>();
        for (String name : playerKillsAndDeathsMap.keySet()) {
            int[] pairKillsDeath = playerKillsAndDeathsMap.get(name);
            if ((pairKillsDeath[0] < 10) || (pairKillsDeath[2] == 0)) {
                continue;
            }
            double killToDeathRatio = (double) pairKillsDeath[1] / (double) pairKillsDeath[2];
            bestPlayers.add(new BestPlayerDTO(name, killToDeathRatio));
        }
        bestPlayers.sort((o1, o2) -> {
            double value1 = o1.getKillToDeathRatio();
            double value2 = o2.getKillToDeathRatio();
            return Double.compare(value2, value1);
        });

        if (bestPlayersCount < bestPlayers.size()) {
            bestPlayers = bestPlayers.subList(0, bestPlayersCount);
        }
        return bestPlayers;
    }
}
