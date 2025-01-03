package com.example.project.repository;

import com.example.project.model.Server;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepository extends CrudRepository<Server, String> {

    Optional<Server> findByEndpoint(String endpoint);
}
