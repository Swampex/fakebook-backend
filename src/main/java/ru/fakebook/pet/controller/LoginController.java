package ru.fakebook.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.fakebook.pet.form.UserForm;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.repository.UserRepository;
import ru.fakebook.pet.security.captcha.CaptchaService;
import ru.fakebook.pet.security.captcha.ICaptchaService;
import ru.fakebook.pet.service.UserService;
import ru.fakebook.pet.transfer.UserRs;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ICaptchaService captchaService;

    @GetMapping("/login")
    public String getLoginPage(Authentication authentication, ModelMap model, HttpServletRequest request) {
//        String response = request.getParameter("g-recaptcha-response");
//        captchaService.processResponse(response);
        if (authentication != null) {
            return "redirect:/profile";
        }
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @GetMapping("/auth/me")
    public UserRs getCurrentUser() {

        User currentUser = userService.defineCurrentUser();
        if (currentUser == null) return UserRs.getNotAuthorizedRs();

        return UserRs.getUserInfoRs(currentUser);
    }
}