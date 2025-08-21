package com.example.renting.controller;

import com.example.renting.dto.BookingDtos;
import com.example.renting.model.Booking;
import com.example.renting.model.User;
import com.example.renting.repo.UserRepository;
import com.example.renting.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    public BookingController(BookingService bookingService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Booking> create(Authentication auth, @RequestBody BookingDtos.CreateBookingRequest req) {
        User tenant = userRepository.findByNickname(auth.getName()).orElseThrow();
        return ResponseEntity.ok(bookingService.createBooking(tenant, req));
    }

    @PostMapping("/complete")
    public ResponseEntity<Booking> complete(@RequestBody BookingDtos.CompleteBookingRequest req) {
        return ResponseEntity.ok(bookingService.completeOrCancel(req));
    }

    @GetMapping("/me")
    public ResponseEntity<List<Booking>> myBookings(Authentication auth) {
        User tenant = userRepository.findByNickname(auth.getName()).orElseThrow();
        return ResponseEntity.ok(bookingService.byTenant(tenant.getId()));
    }
}
