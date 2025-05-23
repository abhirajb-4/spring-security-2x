package com.ust.security2x.controller;

import com.ust.security2x.dto.LoginDto;
import com.ust.security2x.model.User;
import com.ust.security2x.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody User user){
        String msg = userService.addUser(user);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }
    @GetMapping("/user")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/user/login")
    public String userLogin(@RequestBody LoginDto loginDto) {
        String loginMsg = userService.userLogin(loginDto.username(),loginDto.password());
        return loginMsg;
    }
}
