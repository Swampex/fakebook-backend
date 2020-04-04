package ru.fakebook.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.fakebook.pet.model.Photos;
import ru.fakebook.pet.model.Profile;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.repository.UserRepository;
import ru.fakebook.pet.security.details.UserDetailsImpl;
import ru.fakebook.pet.service.UserService;
import ru.fakebook.pet.transfer.UserRs;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Map getUsersPage(@RequestParam(required = false) String page, @RequestParam(required = false) String size) {
        Integer pageInt;
        Integer sizeInt;
        List<User> users;
        Map<String, Object> usersPage = new HashMap<>();

        if (page == null || size == null) {
            pageInt = 1;
            sizeInt = 3;
            users = userService.getUsers(pageInt, sizeInt);
        } else {
            pageInt = Integer.valueOf(page);
            sizeInt = Integer.valueOf(size);

            if (sizeInt > 10) {
                sizeInt = 10;
            }

            users = userService.getUsers(pageInt, sizeInt);
        }

        usersPage.put("users", userService.addFollowedField(users));
        usersPage.put("totalUsersCount", userRepository.count());

        return usersPage;
    }

    @GetMapping("/profile/{id}")
    public User getProfilePageById(@PathVariable("id") Long id) {
        User userProfile = userRepository.getOne(id);

        return userProfile;
    }

    @PostMapping("/user/follow/{id}")
    public UserRs addFollowsList(@PathVariable("id") Long id) {
        User currentUser = userService.defineCurrentUser();
        if (currentUser == null) return UserRs.getNotAuthorizedRs();

        User followed = userRepository.findById(id).get();
        currentUser.getFollows().add(followed);

        userRepository.save(currentUser);

        return UserRs.getOkRs();
    }

    @DeleteMapping("/user/follow/{id}")
    public UserRs deleteIdFromFollowList(@PathVariable("id") Long id) {
        User follower = userService.defineCurrentUser();
        if (follower == null) return UserRs.getNotAuthorizedRs();

        follower.setFollows(
                follower.getFollows().stream()
                .filter( followedUser ->  !id.equals( followedUser.getId() ) )
                .collect(Collectors.toList())
        );

        userRepository.save(follower);

        return UserRs.getOkRs();
    }

    @GetMapping("/profile/status/{id}")
    public HashMap getStatusById(@PathVariable("id") Long id) {
        HashMap<String, String> status = new HashMap<>();

        status.put("status", userRepository.getOne(id).getStatus());

        return status;
    }

    @PutMapping("/profile/status")
    public UserRs setStatus(@RequestBody HashMap<String, String> body) {
        User currentUser = userService.defineCurrentUser();
        if (currentUser == null) return UserRs.getNotAuthorizedRs();
        currentUser.setStatus(body.get("status"));
        userRepository.save(currentUser);
        return UserRs.getOkRs();
    }

    @PutMapping("profile/photo")
    public UserRs addPhoto(@RequestParam("file") MultipartFile file) {

        User currentUser = userService.defineCurrentUser();
        if (currentUser == null) return UserRs.getNotAuthorizedRs();
        String photoLocation = FileController.uploadToLocalFileSystem(file).getBody().toString();

        Photos photos = Photos.builder()
                .photo_large(photoLocation).build();
        currentUser.getProfile().setPhotos(photos);
        userRepository.save(currentUser);

        return UserRs.getOkRs(Collections.singletonMap("filePath", photoLocation));
    }
}