package tn.teriak.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.teriak.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
  Optional<User> findByMatricule(String matricule);

  Boolean existsByUsername(String username);
  Boolean existsByMatricule(String matricule);

  Boolean existsByEmail(String email);
  Optional<User> findByEmail(String username);
}
