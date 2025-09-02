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
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    public BookingController(BookingService bookingService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Booking> create(Authentication auth,
                                          @RequestHeader(value = "X-User-Id", required = false) Long userId,
                                          @RequestBody BookingDtos.CreateBookingRequest req) {
        User tenant;
        if (auth != null) {
            tenant = userRepository.findByNickname(auth.getName()).orElseThrow();
        } else if (userId != null) {
            tenant = userRepository.findById(userId).orElseThrow();
        } else {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(bookingService.createBooking(tenant, req));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> changeStatus(@PathVariable Long id,
                                                @RequestBody BookingDtos.UpdateBookingStatusRequest body) {
        body.bookingId = id;
        return ResponseEntity.ok(bookingService.changeStatus(body));
    }

    @PostMapping("/complete") // для совместимости со старым API
    public ResponseEntity<Booking> complete(@RequestBody BookingDtos.UpdateBookingStatusRequest req) {
        return ResponseEntity.ok(bookingService.changeStatus(req));
    }

    @GetMapping("/me")
    public ResponseEntity<List<Booking>> myBookings(Authentication auth,
                                                    @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        Long tenantId;
        if (auth != null) {
            tenantId = userRepository.findByNickname(auth.getName()).orElseThrow().getId();
        } else if (userId != null) {
            tenantId = userId;
        } else {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(bookingService.byTenant(tenantId));
    }
}
