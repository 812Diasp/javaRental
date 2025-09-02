package com.example.renting.repo;

import com.example.renting.model.Booking;
import com.example.renting.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {}
