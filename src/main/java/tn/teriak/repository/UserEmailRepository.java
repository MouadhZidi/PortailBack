package tn.teriak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.teriak.model.EmailUser;
@Repository
public interface UserEmailRepository extends JpaRepository<EmailUser, Integer> {
	Boolean existsByEmail(String email);

}
