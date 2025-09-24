package com.example.renting.service;

import com.example.renting.model.Booking;
import com.example.renting.model.BookingStatus;
import com.example.renting.model.Property;
import com.example.renting.model.User;
import com.example.renting.repo.BookingRepository;
import com.example.renting.repo.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final AvailabilityService availabilityService;

    public BookingService(BookingRepository bookingRepository, PropertyRepository propertyRepository, AvailabilityService availabilityService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.availabilityService = availabilityService;
    }

    public Booking createBooking(User user, Long propertyId, LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate) || endDate.equals(startDate)) {
            throw new IllegalArgumentException("Дата окончания должна быть после даты начала");
        }

        // Находим property
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property не найдено"));

        // Проверяем доступность дат через AvailabilityService
        boolean isAvailable = availabilityService.isPropertyAvailable(propertyId, startDate, endDate);
        if (!isAvailable) {
            throw new IllegalStateException("Недвижимость уже забронирована на выбранные даты");
        }
        // Проверяем даты
        if (endDate.isBefore(startDate) || endDate.equals(startDate)) {
            throw new IllegalArgumentException("Дата окончания должна быть после даты начала");
        }


        // Рассчитываем стоимость
        long nights = ChronoUnit.DAYS.between(startDate, endDate);
        double totalPrice = nights * property.getPrice();

        // Создаем бронирование
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setProperty(property);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.CREATED);

        return bookingRepository.save(booking);
    }

    private boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                propertyId, startDate, endDate, BookingStatus.CANCELLED);
        return overlappingBookings.isEmpty();
    }

    public Booking changeStatus(Long bookingId, BookingStatus status, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Бронирование не найдено"));

        // Проверяем, что пользователь является владельцем бронирования
        if (!booking.getUser().getId().equals(userId)) {
            throw new SecurityException("Недостаточно прав для изменения этого бронирования");
        }

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}