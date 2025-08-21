package com.example.renting.dto;

import java.time.LocalDate;

public class ListingDtos {
    public static class CreateListingRequest {
        public String city;
        public String country;
        public Double pricePerNight;
        public LocalDate availableFrom;
        public LocalDate availableTo;
    }

    public static class SearchRequest {
        public String city;
        public String country;
        public LocalDate startDate;
        public LocalDate endDate;
        public Double minPrice;
        public Double maxPrice;
        public Double minRating;
    }
}
