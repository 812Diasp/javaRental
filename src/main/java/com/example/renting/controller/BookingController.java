// src/main/java/com/example/renting/controller/BookingController.java

package com.example.renting.controller;

import com.example.renting.dto.BookingDtos;
import com.example.renting.model.Booking;
import com.example.renting.model.User;
import com.example.renting.repo.UserRepository;
import com.example.renting.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

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

    /**
     * Создаёт бронирование на основе аутентифицированного пользователя (JWT)
     */
    @PostMapping
    public ResponseEntity<Booking> create(Authentication auth, @RequestBody BookingDtos.CreateBookingRequest req) {
        if (auth == null || !auth.isAuthenticated()) {
            System.out.println(auth);
            System.out.println("не авторизовано");
            return status(401).build();
        }

        String nickname = auth.getName();
        User tenant = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalStateException("User not found: " + nickname));

        return ok(bookingService.createBooking(tenant, req));
    }

    /**
     * Меняет статус бронирования (например, отмена)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> changeStatus(
            @PathVariable Long id,
            @RequestBody BookingDtos.UpdateBookingStatusRequest body,
            Authentication auth) {

        if (auth == null || !auth.isAuthenticated()) {
            return status(401).build();
        }

        body.bookingId = id;
        return ok(bookingService.changeStatus(body));
    }

    /**
     * Получить все бронирования текущего пользователя
     */
    @GetMapping("/me")
    public ResponseEntity<List<Booking>> myBookings(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return status(401).build();
        }

        String nickname = auth.getName();
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalStateException("User not found: " + nickname));

        return ok(bookingService.byTenant(user.getId()));
    }

    // /complete можно оставить или удалить — зависит от фронтенда
    @PostMapping("/complete")
    public ResponseEntity<Booking> complete(@RequestBody BookingDtos.UpdateBookingStatusRequest req, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return status(401).build();
        }
        return ok(bookingService.changeStatus(req));
    }
}