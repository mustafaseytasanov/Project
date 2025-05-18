package com.example.project.unit.repository;


import com.example.project.model.Server;
import com.example.project.repository.ServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class for unit testing of ServerRepository.
 * @author Mustafa
 * @version 1.0
 */

@DataJpaTest
public class ServerRepositoryUnitTest {

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Server server = new Server();
        server.setEndpoint("167.2.0.32-1937");
        server.setName("testServer");
        entityManager.persist(server);
    }

    @Test
    public void whenFindByEndpointThenReturnServer() {
        String endpoint = "167.2.0.32-1937";
        String name = "testServer";
        Optional<Server> server = serverRepository.findByEndpoint(endpoint);
        // Verifying
        assertThat(server.isPresent()).isTrue();
        assertThat(server.get().getName()).isEqualTo(name);
    }

    @Test
    public void whenNotFindByEndpointThenReturnNull() {
        String endpoint = "167.2.0.32-2010";
        Optional<Server> server = serverRepository.findByEndpoint(endpoint);
        // Verifying
        assertThat(server.isPresent()).isFalse();
    }

}
