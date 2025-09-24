package com.example.renting.service;

import com.example.renting.dto.PropertyDtos.CreatePropertyRequest;
import com.example.renting.model.Property;
import com.example.renting.repo.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final AvailabilityService availabilityService;

    public PropertyService(PropertyRepository propertyRepository, AvailabilityService availabilityService) {
        this.propertyRepository = propertyRepository;
        this.availabilityService = availabilityService;
    }

    public List<Property> all() {
        return propertyRepository.findAll();
    }

    public Property byId(Long id) {
        return propertyRepository.findById(id).orElseThrow();
    }

    public Property create(CreatePropertyRequest req) {
        Property p = new Property();
        p.setTitle(req.title);
        p.setImage(req.image);
        p.setLocation(req.location);
        p.setPrice(req.price);
        p.setBeds(req.beds);
        p.setBaths(req.baths);
        p.setType(req.type);
        p.setRating(req.rating != null ? req.rating : 0.0);
        p.setAmenities(req.amenities);
        return propertyRepository.save(p);
    }

    public boolean checkAvailability(Long propertyId, LocalDate startDate, LocalDate endDate) {
        return availabilityService.isPropertyAvailable(propertyId, startDate, endDate);
    }

    public List<LocalDate> getUnavailableDates(Long propertyId) {
        return availabilityService.getUnavailableDates(propertyId);
    }
}