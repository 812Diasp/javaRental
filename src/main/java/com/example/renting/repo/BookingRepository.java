package com.example.renting.repo;

import com.example.renting.model.Booking;
import com.example.renting.model.BookingStatus;
import com.example.renting.model.Listing;
import com.example.renting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



import com.example.renting.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByProperty(Property property);
    List<Booking> findByTenant(User tenant);
    List<Booking> findByPropertyId(Long propertyId);
    List<Booking> findByTenantId(Long tenantId);
    List<Booking> findByPropertyAndStatus(Property property, BookingStatus status);
    List<Booking> findByPropertyIdAndStatus(Long propertyId, BookingStatus status);


    // Проверка перекрытия периодов: [start, end) против существующих (всё, кроме CANCELLED)
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Booking b " +
            "WHERE b.property.id = :propertyId AND b.status <> com.example.renting.model.BookingStatus.CANCELLED " +
            "AND b.startDate < :endDate AND b.endDate > :startDate")
    boolean hasOverlappingBookings(@Param("propertyId") Long propertyId,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);
}