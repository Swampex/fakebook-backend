package ru.fakebook.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fakebook.pet.form.UserForm;
import ru.fakebook.pet.model.Role;
import ru.fakebook.pet.model.State;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository usersRepository;

    @Override
    public void signUp(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());

        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.USER);

        User user = User.builder()
                .hashPassword(hashPassword)
                .login(userForm.getLogin())
                .roles(roles)
                .state(State.ACTIVE)
                .build();
        usersRepository.save(user);
    }
}
