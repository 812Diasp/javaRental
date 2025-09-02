package com.example.renting.dto;

import jakarta.validation.constraints.*;
import java.util.*;


public class PropertyDtos {
    public static class CreatePropertyRequest {
        @NotBlank public String title;
        @NotBlank public String image;
        @NotBlank public String location;
        @NotNull @Positive public Double price;
        @Min(0) public int beds;
        @Min(0) public int baths;
        @NotBlank public String type;
        @DecimalMin("0.0") @DecimalMax("5.0") public Double rating;
        public java.util.List<String> amenities = new ArrayList<>();
    }
}