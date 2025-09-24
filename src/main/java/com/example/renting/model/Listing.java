package com.example.renting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User owner; // RENTMAN

    @ManyToOne(optional = false)
    private Property property; // связь с Property

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotNull
    private Double pricePerNight;

    @NotNull
    private LocalDate availableFrom;

    @NotNull
    private LocalDate availableTo;

    private Double averageRating = 0.0;

    private Integer ratingsCount = 0;

    private Boolean active = true; // добавляем поле active

    public Listing() {}

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(Double pricePerNight) { this.pricePerNight = pricePerNight; }

    public LocalDate getAvailableFrom() { return availableFrom; }
    public void setAvailableFrom(LocalDate availableFrom) { this.availableFrom = availableFrom; }

    public LocalDate getAvailableTo() { return availableTo; }
    public void setAvailableTo(LocalDate availableTo) { this.availableTo = availableTo; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getRatingsCount() { return ratingsCount; }
    public void setRatingsCount(Integer ratingsCount) { this.ratingsCount = ratingsCount; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
