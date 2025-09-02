package com.example.renting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String image; // URL

    @NotBlank
    private String location; // Город/адрес

    @NotNull
    @Positive
    private Double price; // цена за ночь

    @Min(0)
    private int beds;

    @Min(0)
    private int baths;

    @NotBlank
    private String type; // Apartment, House, Villa и т.п.

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double rating = 0.0;

    @ElementCollection
    @CollectionTable(name = "property_amenities", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "amenity")
    private List<String> amenities = new ArrayList<>();

    // === getters/setters ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public int getBeds() { return beds; }
    public void setBeds(int beds) { this.beds = beds; }

    public int getBaths() { return baths; }
    public void setBaths(int baths) { this.baths = baths; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) {
        this.rating = (rating == null ? 0.0 : rating);
    }

    public List<String> getAmenities() {
        return amenities == null ? new ArrayList<>() : amenities;
    }
    public void setAmenities(List<String> amenities) {
        this.amenities = (amenities == null ? new ArrayList<>() : amenities);
    }
}
