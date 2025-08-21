package com.example.renting.repo;

import com.example.renting.model.Booking;
import com.example.renting.model.BookingStatus;
import com.example.renting.model.Listing;
import com.example.renting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByListingAndStatus(Listing listing, BookingStatus status);
    List<Booking> findByTenant(User tenant);
    List<Booking> findByListing(Listing listing);
    List<Booking> findByListingId(Long listingId);
    List<Booking> findByTenantId(Long tenantId);
    List<Booking> findByListingIdAndStatus(Long listingId, BookingStatus status);
}
