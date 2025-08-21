package com.example.renting.controller;

import com.example.renting.dto.ListingDtos;
import com.example.renting.model.Listing;
import com.example.renting.model.User;
import com.example.renting.repo.UserRepository;
import com.example.renting.service.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;
    private final UserRepository userRepository;

    public ListingController(ListingService listingService, UserRepository userRepository) {
        this.listingService = listingService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('RENTMAN') or hasRole('ADMIN')")
    public ResponseEntity<Listing> create(Authentication auth, @RequestBody ListingDtos.CreateListingRequest req) {
        User owner = userRepository.findByNickname(auth.getName()).orElseThrow();
        return ResponseEntity.ok(listingService.create(owner, req));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Listing>> search(ListingDtos.SearchRequest req) {
        return ResponseEntity.ok(listingService.search(req));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('RENTMAN') or hasRole('ADMIN')")
    public ResponseEntity<List<Listing>> myListings(Authentication auth) {
        User owner = userRepository.findByNickname(auth.getName()).orElseThrow();
        return ResponseEntity.ok(listingService.byOwner(owner));
    }
}
