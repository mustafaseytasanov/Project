package com.example.project.controller;

import com.example.project.dto.ServerDTO;
import com.example.project.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servers")
public class ServerController {

    private final ServerService serverService;
    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PutMapping("/{endpoint}/info")
    public ResponseEntity<Void> putInfo(@PathVariable String endpoint,
                                     @RequestBody ServerDTO serverDTO) { // DTO
        serverService.saveOrUpdateInfo(endpoint, serverDTO);
        return ResponseEntity.ok().build();

    }

}
