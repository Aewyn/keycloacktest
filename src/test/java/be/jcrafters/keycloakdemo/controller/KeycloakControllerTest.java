package be.jcrafters.keycloakdemo.controller;

import be.jcrafters.keycloakdemo.persistence.UserDao;
import be.jcrafters.keycloakdemo.persistence.UserEntity;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KeycloakControllerTest {
	private static final Logger log = LoggerFactory.getLogger(KeycloakControllerTest.class);

	@LocalServerPort
	int port;

	@Autowired
	KeycloakController keycloakController;

	@Autowired
	UserDao userDao;

	private static final UserEntity USER = new UserEntity("Jane");

	@Container
	static KeycloakContainer keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:25.0")
			.withAdminUsername("AdminDoe")
			.withAdminPassword("top$3cret");

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3-alpine");

	@BeforeEach
	void beforeEach(){
		userDao.save(USER);
	}

	@AfterEach
	void afterEach(){
		userDao.deleteAll();
	}

	@Test
	void assertContainerIsRunning() {
		assertThat(keycloakContainer.isRunning()).isTrue();
		assertThat(postgreSQLContainer.isRunning()).isTrue();
		log.info(keycloakContainer.getAuthServerUrl());
		log.info(keycloakContainer.getAdminUsername());
	}

	@Test
	void assertGetUsersReturnsCorrectUsers(){
		var users = keycloakController.getUsers();
		assertThat(users).hasSize(1);
		var first = users.getFirst();
		assertThat(first.getName()).isEqualTo("Jane");
	}
}