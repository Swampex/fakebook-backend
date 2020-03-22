package ru.fakebook.pet.service;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fakebook.pet.form.UserForm;
import ru.fakebook.pet.model.Profile;
import ru.fakebook.pet.model.Role;
import ru.fakebook.pet.model.State;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.repository.ProfileRepository;
import ru.fakebook.pet.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void signUp(UserForm userForm) throws PSQLException {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());

        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.USER);

        User user = User.builder()
                .hashPassword(hashPassword)
                .login(userForm.getLogin())
                .email(userForm.getEmail())
                .roles(roles)
                .state(State.ACTIVE)
                .build();

        Profile profile = Profile.builder()
                .user(user)
                .lookingForAJob(false)
                .build();
        usersRepository.save(user);
        profileRepository.save(profile);
    }
}
