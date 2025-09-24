package com.example.renting.dto;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityDtos {

    public static class AvailabilityResponse {
        public boolean available;
        public String message;

        public AvailabilityResponse(boolean available, String message) {
            this.available = available;
            this.message = message;
        }
    }

    public static class PropertyWithAvailabilityResponse {
        public Long id;
        public String title;
        public String image;
        public String location;
        public Double price;
        public int beds;
        public int baths;
        public String type;
        public Double rating;
        public List<String> amenities;
        public List<LocalDate> unavailableDates; // Добавляем занятые даты
    }
}