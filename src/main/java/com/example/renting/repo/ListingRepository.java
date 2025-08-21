package com.example.renting.repo;

import com.example.renting.model.Listing;
import com.example.renting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    @Query("select l from Listing l where " +
           "(:city is null or lower(l.city) = lower(:city)) and " +
           "(:country is null or lower(l.country) = lower(:country)) and " +
           "(:minPrice is null or l.pricePerNight >= :minPrice) and " +
           "(:maxPrice is null or l.pricePerNight <= :maxPrice) and " +
           "(:minRating is null or l.averageRating >= :minRating) and " +
           "( (:startDate is null and :endDate is null) or (l.availableFrom <= :startDate and l.availableTo >= :endDate) )")
    List<Listing> search(@Param("city") String city,
                         @Param("country") String country,
                         @Param("startDate") LocalDate startDate,
                         @Param("endDate") LocalDate endDate,
                         @Param("minPrice") Double minPrice,
                         @Param("maxPrice") Double maxPrice,
                         @Param("minRating") Double minRating);

    List<Listing> findByOwner(User owner);
}
