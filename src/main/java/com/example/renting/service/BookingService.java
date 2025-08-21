package com.example.renting.service;

import com.example.renting.dto.BookingDtos;
import com.example.renting.model.*;
import com.example.renting.repo.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ListingService listingService;

    public BookingService(BookingRepository bookingRepository, ListingService listingService) {
        this.bookingRepository = bookingRepository;
        this.listingService = listingService;
    }

    @Transactional
    public Booking createBooking(User tenant, BookingDtos.CreateBookingRequest req) {
        Listing listing = listingService.get(req.listingId);
        // naive availability check: ensure within available window and no completed bookings overlapping
        if (req.startDate.isBefore(listing.getAvailableFrom()) || req.endDate.isAfter(listing.getAvailableTo())) {
            throw new IllegalArgumentException("Dates out of available range");
        }
        Booking b = new Booking();
        b.setListing(listing);
        b.setTenant(tenant);
        b.setStartDate(req.startDate);
        b.setEndDate(req.endDate);
        long nights = ChronoUnit.DAYS.between(req.startDate, req.endDate);
        b.setTotalPrice(nights * listing.getPricePerNight());
        return bookingRepository.save(b);
    }

    @Transactional
    public Booking completeOrCancel(BookingDtos.CompleteBookingRequest req) {
        Booking b = bookingRepository.findById(req.bookingId).orElseThrow();
        b.setStatus(req.cancelled ? BookingStatus.CANCELLED : BookingStatus.COMPLETED);
        return bookingRepository.save(b);
    }

    public List<Booking> byTenant(Long tenantId) { return bookingRepository.findByTenantId(tenantId); }
    public List<Booking> byListing(Long listingId) { return bookingRepository.findByListingId(listingId); }
}
