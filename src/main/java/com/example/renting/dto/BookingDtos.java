package com.example.renting.dto;

import java.time.LocalDate;



import com.example.renting.model.BookingStatus;
import jakarta.validation.constraints.*;



public class BookingDtos {
    // public static class CreateBookingRequest {
    //     @NotNull public Long propertyId;
    //     @NotNull public LocalDate startDate;
    //     @NotNull public LocalDate endDate;
    // }
public static class CreateBookingRequest {
    private Long listingId; // ← БЫЛО propertyId

    private LocalDate startDate;
    private LocalDate endDate;

    // Геттеры и сеттеры
    public Long getListingId() { return listingId; }
    public void setListingId(Long listingId) { this.listingId = listingId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
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