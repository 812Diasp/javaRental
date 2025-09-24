package com.example.renting.dto;
import java.time.LocalDate;

public class BookingRequest {
    private Long propertyId;
    private LocalDate startDate;
    private LocalDate endDate;

    // Геттеры и сеттеры
    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}