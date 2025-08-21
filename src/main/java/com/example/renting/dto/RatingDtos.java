package com.example.renting.dto;

public class RatingDtos {
    public static class RateRequest {
        public Long bookingId;
        public Long toUserId;
        public int stars; // 1..5
    }
}
