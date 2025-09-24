package com.example.renting.repo;

import com.example.renting.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PropertyRepository extends JpaRepository<Property, Long> {}
