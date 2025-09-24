package com.example.renting.repo;

import com.example.renting.model.Booking;
import com.example.renting.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    @Query("SELECT b FROM Booking b WHERE b.property.id = :propertyId " +
            "AND b.startDate < :endDate AND b.endDate > :startDate " +
            "AND b.status != :cancelledStatus")
    List<Booking> findOverlappingBookings(
            @Param("propertyId") Long propertyId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("cancelledStatus") BookingStatus cancelledStatus);

    // Новый метод: найти все активные бронирования для property
    @Query("SELECT b FROM Booking b WHERE b.property.id = :propertyId " +
            "AND b.status != com.example.renting.model.BookingStatus.CANCELLED " +
            "AND b.endDate >= :today")
    List<Booking> findActiveBookingsByPropertyId(
            @Param("propertyId") Long propertyId,
            @Param("today") LocalDate today);
}