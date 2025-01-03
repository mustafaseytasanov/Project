package com.example.project.service;

import com.example.project.dto.ServerDTO;
import com.example.project.model.Server;
import com.example.project.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerService {
    private final ServerRepository serverRepository;
    @Autowired
    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public void saveOrUpdateInfo(String endpoint,
                                 ServerDTO serverDTO) {
        Server server = serverRepository.findByEndpoint(endpoint)
                .orElse(new Server(endpoint));
        server.setName(serverDTO.getName());
        server.setGameModes(serverDTO.getGameModes());
        serverRepository.save(server);
    }
}
