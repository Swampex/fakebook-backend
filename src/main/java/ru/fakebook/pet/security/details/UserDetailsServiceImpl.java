package ru.fakebook.pet.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fakebook.pet.model.User;
import ru.fakebook.pet.repository.UserRepository;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userCandidate = userRepository.findOneByLogin(login);
        if(userCandidate.isPresent()) {
            return new UserDetailsImpl(userCandidate.get());
        }
        else throw new IllegalArgumentException("User not found");
    }
}
