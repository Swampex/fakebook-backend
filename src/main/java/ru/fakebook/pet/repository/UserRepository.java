package ru.fakebook.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fakebook.pet.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByLogin(String login);
}
