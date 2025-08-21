package com.example.renting.repo;

import com.example.renting.model.Rating;
import com.example.renting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByToUser(User toUser);
}
