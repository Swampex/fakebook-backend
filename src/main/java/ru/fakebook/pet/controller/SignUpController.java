package ru.fakebook.pet.controller;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.fakebook.pet.form.UserForm;
import ru.fakebook.pet.repository.UserRepository;
import ru.fakebook.pet.service.SignUpService;
import ru.fakebook.pet.service.UserService;
import ru.fakebook.pet.transfer.UserRs;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class SignUpController {

    @Autowired
    private SignUpService service;

    @Autowired
    UserService userService;

    @PostMapping("/signUp")
    public UserRs signUpUser(@RequestBody UserForm userForm) {

        try {
            service.signUp(userForm);
        } catch (Exception e) {
            return UserRs.getExceptionRs(e);
        }

        return UserRs.getOkRs();
    }
}