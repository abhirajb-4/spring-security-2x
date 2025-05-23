package com.ust.security2x.service;

import com.ust.security2x.model.User;
import com.ust.security2x.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public String addUser(User user) {
        try {
            userRepository.save(user);
            return "User saved";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
