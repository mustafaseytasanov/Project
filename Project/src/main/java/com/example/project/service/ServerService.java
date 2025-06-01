package com.example.project.service;

import com.example.project.dto.InfoDTO;
import com.example.project.dto.PopularServerDTO;
import com.example.project.dto.ServerDTO;
import com.example.project.dto.StatsDTO;

import java.util.List;

/**
 * Interface ServerService
 * @author Mustafa
 * @version 1.0
 */
public interface ServerService {
    void saveOrUpdateInfo(String endpoint, InfoDTO infoDTO);
    InfoDTO getOneServerInfo(String endpoint);
    List<ServerDTO> getAllServersInfo();
    boolean isEmptyServer(String endpoint);
    StatsDTO getStats(String endpoint);
    List<PopularServerDTO> getPopularServers(int popularServersCount);

}
