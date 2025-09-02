package com.example.renting.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;


@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Property property;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User tenant;


    @NotNull
    private LocalDate startDate;


    @NotNull
    private LocalDate endDate; // endDate — дата выезда (не включительно по расчёту ночей)


    @Positive
    private Double totalPrice;


    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.CREATED;


    public Booking() {}


    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }


    public User getTenant() { return tenant; }
    public void setTenant(User tenant) { this.tenant = tenant; }


    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }


    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }


    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }


    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}