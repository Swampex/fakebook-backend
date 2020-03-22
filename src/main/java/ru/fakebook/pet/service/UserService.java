package ru.fakebook.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.repository.UserRepository;
import ru.fakebook.pet.security.details.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers(Integer page, Integer size) {
        List<User> usersResult = new ArrayList<>();

        List<User> allUsers = userRepository.findAll();
        User currentUser = defineCurrentUser();
        if(currentUser != null) {
            allUsers = allUsers.stream()
                    .filter(u -> !u.getId().equals(currentUser.getId())).collect(Collectors.toList());
        }

        for (int currentPage = 0; currentPage < page; currentPage++) {
            if ( currentPage+1 == page && usersResult.isEmpty()) {
                for (int i = 0; i < size; i++) {
                    try {
                        usersResult.add(allUsers.get( currentPage*size + i ));
                    }
                    catch (IndexOutOfBoundsException iob) {
                        return usersResult;
                    }
                }
            }
        }

        return usersResult;
    }

    public List<User> addFollowedField(List<User> users) {

        User currentUser = defineCurrentUser();
        if (currentUser == null) return users;

        List<User> result = users.stream().peek(u -> {
            if (currentUser.getFollows().indexOf(u) != -1) {
                u.setFollowed(true);
            } else {
                u.setFollowed(false);
            }
        }).collect(Collectors.toList());

        return result;
    }

    public User defineCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null
                || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
        return userRepository.getOne(principal.getUser().getId());
    }
}
