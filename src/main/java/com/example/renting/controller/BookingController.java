package com.example.renting.controller;

import com.example.renting.model.Booking;
import com.example.renting.model.BookingStatus;
import com.example.renting.model.User;
import com.example.renting.repo.UserRepository;
import com.example.renting.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Booking> createBooking(
            Authentication authentication,
            @RequestBody Map<String, Object> request) {
        
        String username = authentication.getName();
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        Long propertyId = Long.valueOf(request.get("propertyId").toString());
        LocalDate startDate = LocalDate.parse(request.get("startDate").toString());
        LocalDate endDate = LocalDate.parse(request.get("endDate").toString());

        Booking booking = bookingService.createBooking(user, propertyId, startDate, endDate);
        return ResponseEntity.ok(booking);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        
        String username = authentication.getName();
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        BookingStatus status = BookingStatus.valueOf(request.get("status"));
        Booking booking = bookingService.changeStatus(id, status, user.getId());
        
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Booking>> getUserBookings(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        List<Booking> bookings = bookingService.getUserBookings(user.getId());
        return ResponseEntity.ok(bookings);
    }
}