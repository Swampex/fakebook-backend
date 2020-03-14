package ru.fakebook.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.fakebook.pet.form.UserForm;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.service.SignUpService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class SignUpController {

    @Autowired
    private SignUpService service;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUpUser(@RequestBody UserForm userForm) {

        service.signUp(userForm);
        return "OK";
    }
}