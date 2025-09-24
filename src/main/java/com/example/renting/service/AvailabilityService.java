package com.example.renting.service;

import com.example.renting.model.Booking;
import com.example.renting.model.BookingStatus;
import com.example.renting.repo.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityService {
    private final BookingRepository bookingRepository;

    public AvailabilityService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Проверяет, доступно ли property для бронирования на указанные даты
     */
    public boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                propertyId, startDate, endDate, BookingStatus.CANCELLED);
        return overlappingBookings.isEmpty();
    }

    /**
     * Возвращает список всех занятых периодов для property
     */
    public List<Booking> getBookedPeriods(Long propertyId) {
        return bookingRepository.findActiveBookingsByPropertyId(propertyId, LocalDate.now());
    }

    /**
     * Возвращает список дат, когда property занято
     */
    public List<LocalDate> getUnavailableDates(Long propertyId) {
        List<Booking> activeBookings = getBookedPeriods(propertyId);
        return activeBookings.stream()
                .flatMap(booking -> booking.getStartDate().datesUntil(booking.getEndDate().plusDays(1)))
                .distinct()
                .toList();
    }
}