package com.example.renting.dto;

import java.time.LocalDate;



import com.example.renting.model.BookingStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;


public class BookingDtos {
    public static class CreateBookingRequest {
        @NotNull public Long propertyId;
        @NotNull public LocalDate startDate;
        @NotNull public LocalDate endDate;
    }


    public static class UpdateBookingStatusRequest {
        @NotNull public Long bookingId;
        @NotNull public BookingStatus status; // CONFIRMED или CANCELLED
    }


    public static class BookingResponse {
        public Long id;
        public Long propertyId;
        public Long tenantId;
        public LocalDate startDate;
        public LocalDate endDate;
        public Double totalPrice;
        public BookingStatus status;
    }
}