package com.example.renting.controller;

import com.example.renting.dto.RatingDtos;
import com.example.renting.model.Rating;
import com.example.renting.model.User;
import com.example.renting.repo.UserRepository;
import com.example.renting.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final UserRepository userRepository;

    public RatingController(RatingService ratingService, UserRepository userRepository) {
        this.ratingService = ratingService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Rating> rate(Authentication auth, @RequestBody RatingDtos.RateRequest req) {
        User from = userRepository.findByNickname(auth.getName()).orElseThrow();
        return ResponseEntity.ok(ratingService.rate(from, req));
    }
}
