package be.jcrafters.keycloakdemo.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique=true,name = "first_name")
	private String name;

	public UserEntity(){}

	public UserEntity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
