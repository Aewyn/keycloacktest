package be.jcrafters.keycloakdemo.controller;

import be.jcrafters.keycloakdemo.persistence.UserDao;
import be.jcrafters.keycloakdemo.persistence.UserEntity;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keycloak")
public class KeycloakController {

	private final UserDao userDao;

	public KeycloakController(UserDao userDao) {
		this.userDao = userDao;
	}

	@GetMapping("/users")
	public List<UserEntity> getUsers() {
		return userDao.findAll();
	}
}
