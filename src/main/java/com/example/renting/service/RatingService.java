package com.example.renting.service;

import com.example.renting.dto.RatingDtos;
import com.example.renting.model.*;
import com.example.renting.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Rating rate(User fromUser, RatingDtos.RateRequest req) {
        User toUser = userRepository.findById(req.toUserId).orElseThrow();
        Booking booking = bookingRepository.findById(req.bookingId).orElseThrow();

        Rating rating = new Rating();
        rating.setFromUser(fromUser);
        rating.setToUser(toUser);
        rating.setBooking(booking);
        rating.setStars(req.stars);

        rating = ratingRepository.save(rating);

        // Обновляем агрегированные данные
        double total = toUser.getAverageRating() * toUser.getRatingsCount() + req.stars;
        int count = toUser.getRatingsCount() + 1;
        toUser.setRatingsCount(count);
        toUser.setAverageRating(total / count);

        if (toUser.getAverageRating() < 2.0) {
            toUser.setBlocked(true);
        }

        userRepository.save(toUser);
        return rating;
    }

    public List<Rating> byUser(User u) {
        return ratingRepository.findByToUser(u);
    }
}
