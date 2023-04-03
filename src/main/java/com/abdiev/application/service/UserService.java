package com.abdiev.application.service;

import com.abdiev.application.entity.*;
import com.abdiev.application.exception.UserAuthenticationException;
import com.abdiev.application.payload.AuthenticationRequest;
import com.abdiev.application.payload.RegistrationRequest;
import com.abdiev.application.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private  PasswordEncoder encoder;

    public UserService(UserRepository userRepository,@Lazy PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    protected void init() {
        User user = User.builder()
                .id(1L)
                .email("abdiev0225@gmail.com")
                .profile("Java Developer")
                .password("$2a$12$IHyAIYG2bUFKl2nEntcK0u4rr7bcumbp7TtcWqlzZqIcK7dsowi52")
                .username("abdiev")
                .fullName("Atai Abdiev")
                .role(Role.ROLE_ADMIN)
                .build();
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserAuthenticationException("User with login %s has not been found".formatted(username), HttpStatus.BAD_REQUEST)
        );
    }

    public User login(AuthenticationRequest authenticationRequest) {
        User user = (User) loadUserByUsername(authenticationRequest.getLogin());
        if(encoder.matches(authenticationRequest.getPassword(),user.getPassword())) {
            return user;
        }
        throw new UserAuthenticationException("Invalid credentials",HttpStatus.BAD_REQUEST);
    }
    public User signUp(RegistrationRequest registrationRequest) {
        Optional<User> user = userRepository.findByUsername(registrationRequest.getUsername());
        if (user.isPresent()) {
            throw new UserAuthenticationException("User with username %s has been registered already",
                    HttpStatus.BAD_REQUEST);
        }
        User userToBeSaved = User.builder()
                .email(registrationRequest.getEmail())
                .fullName(registrationRequest.getFullName())
                .profile(registrationRequest.getProfile())
                .password(encoder.encode(registrationRequest.getPassword()))
                .username(registrationRequest.getUsername())
                .build();
        return userRepository.save(userToBeSaved);
    }
}
