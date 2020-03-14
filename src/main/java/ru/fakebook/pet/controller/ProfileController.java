package ru.fakebook.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.fakebook.pet.model.Profile;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.repository.ProfileRepository;
import ru.fakebook.pet.repository.UserRepository;
import ru.fakebook.pet.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

//    @GetMapping("/profile/{id}")
//    public Profile getProfilePageById(@PathVariable("id") Long id) {
//
//        Profile userProfile = profileRepository.getOne(id);
//
//        return userProfile;
//    }

}
