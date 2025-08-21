package com.example.renting.controller;

import com.example.renting.model.User;
import com.example.renting.repo.UserRepository;
import com.example.renting.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(Authentication auth) {
        User u = userRepository.findByNickname(auth.getName()).orElseThrow();
        return ResponseEntity.ok(u);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateMe(Authentication auth, @RequestBody User req) {
        User u = userRepository.findByNickname(auth.getName()).orElseThrow();
        u.setFullName(req.getFullName());
        u.setCity(req.getCity());
        u.setCountry(req.getCountry());
        u.setBirthDate(req.getBirthDate());
        u.setEmail(req.getEmail());
        return ResponseEntity.ok(userService.save(u));
    }
}
