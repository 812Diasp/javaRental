package com.example.renting.dto;

import java.time.LocalDate;

public class BookingDtos {
    public static class CreateBookingRequest {
        public Long listingId;
        public LocalDate startDate;
        public LocalDate endDate;
    }

    public static class CompleteBookingRequest {
        public Long bookingId;
        public boolean cancelled;
    }
}
