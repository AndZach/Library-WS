package gr.andzach.libraryws.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.andzach.libraryws.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
}
