package ru.fakebook.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fakebook.pet.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {


}
