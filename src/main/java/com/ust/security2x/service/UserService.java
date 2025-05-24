package com.ust.security2x.service;

import com.ust.security2x.model.User;
import com.ust.security2x.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;


    public String addUser(User user) {
        try {
            Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
            if(existingUser.isPresent()){
                return "User already Exists";
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User saved";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String userLogin(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User user = userRepository.findByUsername(username).orElseThrow();

        if ("ROLE_ADMIN".equals(user.getRole())) {
            return "hello admin";
        } else if ("ROLE_USER".equals(user.getRole())) {
            return "hello user";
        } else {
            return "hello guest";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
