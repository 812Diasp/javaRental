package com.example.renting.controller;

import com.example.renting.dto.AvailabilityDtos;
import com.example.renting.model.Property;
import com.example.renting.service.PropertyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // Все property (для главной страницы)
    @GetMapping
    public ResponseEntity<List<Property>> getAll() {
        return ResponseEntity.ok(propertyService.all());
    }

    // Детали конкретного property
    @GetMapping("/{id}")
    public ResponseEntity<Property> getById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.byId(id));
    }

    // Проверка доступности property на определенные даты
    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        boolean isAvailable = propertyService.checkAvailability(id, startDate, endDate);
        return ResponseEntity.ok(isAvailable);
    }

    // Получение списка занятых дат для property
    @GetMapping("/{id}/unavailable-dates")
    public ResponseEntity<List<LocalDate>> getUnavailableDates(@PathVariable Long id) {
        List<LocalDate> unavailableDates = propertyService.getUnavailableDates(id);
        return ResponseEntity.ok(unavailableDates);
    }

    // Получение property с информацией о занятых датах
    @GetMapping("/{id}/with-availability")
    public ResponseEntity<AvailabilityDtos.PropertyWithAvailabilityResponse> getPropertyWithAvailability(@PathVariable Long id) {
        Property property = propertyService.byId(id);
        List<LocalDate> unavailableDates = propertyService.getUnavailableDates(id);

        AvailabilityDtos.PropertyWithAvailabilityResponse response = new AvailabilityDtos.PropertyWithAvailabilityResponse();
        response.id = property.getId();
        response.title = property.getTitle();
        response.image = property.getImage();
        response.location = property.getLocation();
        response.price = property.getPrice();
        response.beds = property.getBeds();
        response.baths = property.getBaths();
        response.type = property.getType();
        response.rating = property.getRating();
        response.amenities = property.getAmenities();
        response.unavailableDates = unavailableDates;

        return ResponseEntity.ok(response);
    }
}