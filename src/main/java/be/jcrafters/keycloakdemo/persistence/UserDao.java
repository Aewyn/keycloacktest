package be.jcrafters.keycloakdemo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long> {
}
