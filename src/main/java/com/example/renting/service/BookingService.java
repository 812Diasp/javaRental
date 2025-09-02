package com.example.renting.service;

import com.example.renting.dto.BookingDtos.*;
import com.example.renting.model.*;
import com.example.renting.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;

    public BookingService(BookingRepository bookingRepository, PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }

    public Booking createBooking(User tenant, CreateBookingRequest req) {
        if (req.endDate.isBefore(req.startDate) || req.endDate.equals(req.startDate)) {
            throw new IllegalArgumentException("endDate must be after startDate");
        }
        Property prop = propertyRepository.findById(req.propertyId).orElseThrow();

        boolean overlaps = bookingRepository.hasOverlappingBookings(prop.getId(), req.startDate, req.endDate);
        if (overlaps) {
            throw new IllegalStateException("Selected dates are not available");
        }

        long nights = ChronoUnit.DAYS.between(req.startDate, req.endDate);
        double total = nights * prop.getPrice();

        Booking b = new Booking();
        b.setProperty(prop);
        b.setTenant(tenant);
        b.setStartDate(req.startDate);
        b.setEndDate(req.endDate);
        b.setTotalPrice(total);
        b.setStatus(BookingStatus.CREATED);
        return bookingRepository.save(b);
    }

    public Booking changeStatus(UpdateBookingStatusRequest req) {
        Booking b = bookingRepository.findById(req.bookingId).orElseThrow();
        if (b.getStatus() == BookingStatus.CANCELLED) {
            return b; // уже отменено
        }
        b.setStatus(req.status);
        return bookingRepository.save(b);
    }

    public List<Booking> byTenant(Long tenantId) {
        return bookingRepository.findByTenantId(tenantId);
    }

    public List<Booking> byProperty(Long propertyId) {
        return bookingRepository.findByPropertyId(propertyId);
    }
}
