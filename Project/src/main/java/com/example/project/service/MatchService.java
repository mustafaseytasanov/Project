package com.example.project.service;

import com.example.project.dto.MatchDTO;
import com.example.project.dto.ReportDTO;
import com.example.project.model.Match;

import java.util.List;

/**
 * Interface MatchService
 * @author Mustafa
 * @version 1.0
 */
public interface MatchService {
    void addMatch(String endpoint, String timeStamp, MatchDTO matchDTO);
    Match getMatch(String endpoint, String timeStamp);
    List<ReportDTO> getRecentMatches(int recentMatchesCount);

}
