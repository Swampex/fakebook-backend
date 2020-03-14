package ru.fakebook.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.repository.UserRepository;
import ru.fakebook.pet.security.details.UserDetailsImpl;
import ru.fakebook.pet.transfer.AuthMeDto;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class LoginController {

    @Autowired UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage(Authentication authentication, ModelMap model, HttpServletRequest request) {
        if (authentication != null) {
            return "redirect:/profile";
        }
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @GetMapping("/auth/me")
    public AuthMeDto getCurrentUser() {
        Integer resultCode = 0;
        List<String> messages = new ArrayList<>();

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof String) {
            resultCode = 1;
            messages.add("You are not authorized");
            return AuthMeDto.from(resultCode, messages);
        }

        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return AuthMeDto.from(userRepository.findById(principal.getUser().getId()).get());
    }
}