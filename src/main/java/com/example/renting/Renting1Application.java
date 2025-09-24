package com.example.renting;

import com.example.renting.model.*;
import com.example.renting.repo.PropertyRepository;
import com.example.renting.repo.ListingRepository;
import com.example.renting.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Renting1Application {

    public static void main(String[] args) {
        SpringApplication.run(Renting1Application.class, args);
    }

    @Bean
    CommandLineRunner initData(UserService userService,
                               PropertyRepository propertyRepository,
                               ListingRepository listingRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            // ================= USERS =================
            createUserIfNotExists(userService, passwordEncoder, "admin", "admin@example.com", Role.ADMIN, "Admin User", "Moscow", "Russia", "admin123");
            createUserIfNotExists(userService, passwordEncoder, "testuser", "user@example.com", Role.USER, "Test User", "Moscow", "Russia", "user123");
            createUserIfNotExists(userService, passwordEncoder, "rentman", "rentman@example.com", Role.RENTMAN, "Rent Manager", "Moscow", "Russia", "rent123");

            // ================= PROPERTIES =================
            if (propertyRepository.count() == 0) {
                List<Property> properties = Arrays.asList(
                    makeProperty("Wooden Eco Cabin", "Forest Retreat, Oregon", 120.0, 4.8,
                            "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                            "Cabin", 2, 1, Arrays.asList("Wifi", "Kitchen", "Parking")),
                    makeProperty("Treehouse Getaway", "Redwood Forest, California", 195.0, 4.9,
                            "https://images.unsplash.com/photo-1519643225200-94e79e383724?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                            "Treehouse", 1, 1, Arrays.asList("Wifi", "Hot tub", "Breakfast")),
                    makeProperty("Lakeside Cottage", "Lake Tahoe, Nevada", 230.0, 4.7,
                            "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                            "Cottage", 3, 2, Arrays.asList("Wifi", "Kitchen", "Fireplace", "Beach access")),
                    makeProperty("Modern City Loft", "Downtown Chicago, Illinois", 175.0, 4.6,
                            "https://images.unsplash.com/photo-1493809842364-78817add7ffb?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                            "Apartment", 1, 1, Arrays.asList("Wifi", "Gym", "Pool", "Workspace"))
                );

                // Сохраняем properties и получаем их с ID
                List<Property> savedProperties = propertyRepository.saveAll(properties);
                System.out.println("Inserted sample properties: " + savedProperties.size());

                // ================= LISTINGS =================
                User rentman = userService.findByNickname("rentman")
                        .orElseThrow(() -> new RuntimeException("Rentman user not found"));

                System.out.println("Listings before insert: " + listingRepository.count());

                for (Property property : savedProperties) {
                    Listing listing = new Listing();
                    listing.setOwner(rentman);
                    listing.setProperty(property);
                    listing.setCity(extractCityFromLocation(property.getLocation()));
                    listing.setCountry("USA");
                    listing.setPricePerNight(property.getPrice());
                    listing.setAvailableFrom(LocalDate.now());
                    listing.setAvailableTo(LocalDate.now().plusMonths(1));
                    listing.setActive(true);
                    
                    listingRepository.save(listing);
                    System.out.println("Created listing for property: " + property.getTitle());
                }

                System.out.println("Listings after insert: " + listingRepository.count());
                System.out.println("--------------------------------------------------------------------------------------");

                // Вывод всех листингов
                listingRepository.findAll().forEach(listing ->
                    System.out.println("Listing ID: " + listing.getId() +
                            ", Property: " + listing.getProperty().getTitle() +
                            ", Owner: " + listing.getOwner().getNickname() +
                            ", City: " + listing.getCity() +
                            ", Price per night: " + listing.getPricePerNight())
                );
            }
        };
    }

    private static void createUserIfNotExists(UserService userService, PasswordEncoder passwordEncoder, 
                                            String nickname, String email, Role role,
                                            String fullName, String city, String country, String password) {
        if (userService.findByNickname(nickname).isEmpty()) {
            User user = new User();
            user.setNickname(nickname);
            user.setEmail(email);
            user.setPassword("temp_password"); // Временный пароль, будет закодирован в register()
            user.setRole(role);
            user.setFullName(fullName);
            user.setCity(city);
            user.setCountry(country);
            user.setBlocked(false);
            user.setBirthDate(LocalDate.now().minusYears(25)); // Добавляем дату рождения
            
            // Используем register() который закодирует пароль
            userService.register(user, password);
            System.out.println("Created user: " + nickname + " / " + password);
        }
    }

    private static Property makeProperty(String title, String location, Double price, Double rating,
                                       String image, String type, int beds, int baths, List<String> amenities) {
        Property property = new Property();
        property.setTitle(title);
        property.setLocation(location);
        property.setPrice(price);
        property.setRating(rating);
        property.setImage(image);
        property.setType(type);
        property.setBeds(beds);
        property.setBaths(baths);
        property.setAmenities(amenities);
        return property;
    }

    private static String extractCityFromLocation(String location) {
        // Простая логика для извлечения города из location
        if (location.contains(",")) {
            return location.split(",")[0].trim();
        }
        return location;
    }
}