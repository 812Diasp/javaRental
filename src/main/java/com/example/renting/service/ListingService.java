package com.example.renting.service;

import com.example.renting.dto.ListingDtos;
import com.example.renting.model.Listing;
import com.example.renting.model.User;
import com.example.renting.repo.ListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Listing create(User owner, ListingDtos.CreateListingRequest req) {
        Listing l = new Listing();
        l.setOwner(owner);
        l.setCity(req.city);
        l.setCountry(req.country);
        l.setPricePerNight(req.pricePerNight);
        l.setAvailableFrom(req.availableFrom);
        l.setAvailableTo(req.availableTo);
        return listingRepository.save(l);
    }

    public List<Listing> search(ListingDtos.SearchRequest req) {
        return listingRepository.search(req.city, req.country, req.startDate, req.endDate, req.minPrice, req.maxPrice, req.minRating);
    }

    public List<Listing> byOwner(User owner) { return listingRepository.findByOwner(owner); }

    public Listing save(Listing l) { return listingRepository.save(l); }

    public Listing get(Long id) { return listingRepository.findById(id).orElseThrow(); }
}
