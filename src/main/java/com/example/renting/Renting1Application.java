package com.example.renting;

import com.example.renting.model.Property;
import com.example.renting.model.Role;
import com.example.renting.model.User;
import com.example.renting.repo.PropertyRepository;
import com.example.renting.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Renting1Application {

    public static void main(String[] args) {
        SpringApplication.run(Renting1Application.class, args);
    }

    @Bean
    CommandLineRunner initData(UserService userService, PropertyRepository propertyRepository) {
        return args -> {
            // === USERS ===
            if (userService.findByNickname("admin").isEmpty()) {
                User admin = new User();
                admin.setNickname("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword("temp_password");
                admin.setRole(Role.ADMIN);
                admin.setFullName("Admin User");
                admin.setCity("Moscow");
                admin.setCountry("Russia");
                admin.setBlocked(false);

                userService.register(admin, "admin123");
                System.out.println("Created admin user: admin / admin123");
            }

            if (userService.findByNickname("testuser").isEmpty()) {
                User testUser = new User();
                testUser.setNickname("testuser");
                testUser.setEmail("user@example.com");
                testUser.setPassword("temp_password");
                testUser.setRole(Role.USER);
                testUser.setFullName("Test User");
                testUser.setCity("Moscow");
                testUser.setCountry("Russia");
                testUser.setBlocked(false);

                userService.register(testUser, "user123");
                System.out.println("Created test user: testuser / user123");
            }

            if (userService.findByNickname("rentman").isEmpty()) {
                User rentman = new User();
                rentman.setNickname("rentman");
                rentman.setEmail("rentman@example.com");
                rentman.setPassword("temp_password");
                rentman.setRole(Role.USER);
                rentman.setFullName("Rent Manager");
                rentman.setCity("Moscow");
                rentman.setCountry("Russia");
                rentman.setBlocked(false);

                userService.register(rentman, "rent123");
                System.out.println("Created rentman user: rentman / rent123");
            }

            // === PROPERTIES ===
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
                                "Apartment", 1, 1, Arrays.asList("Wifi", "Gym", "Pool", "Workspace")),

                        makeProperty("Desert Adobe House", "Sedona, Arizona", 210.0, 4.9,
                                "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                                "House", 2, 2, Arrays.asList("Wifi", "Patio", "Mountain views", "Hot tub")),

                        makeProperty("Beachfront Villa", "Malibu, California", 450.0, 4.95,
                                "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                                "Villa", 4, 3, Arrays.asList("Private beach", "Infinity pool", "Chef's kitchen", "Ocean view")),

                        makeProperty("Alpine Ski Chalet", "Aspen, Colorado", 320.0, 4.8,
                                "https://images.unsplash.com/photo-1511884642898-4c92249e20b6?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                                "Chalet", 3, 2, Arrays.asList("Ski-in/ski-out", "Fireplace", "Sauna", "Mountain view")),

                        makeProperty("Historic Brownstone", "Brooklyn, New York", 275.0, 4.7,
                                "https://images.unsplash.com/photo-1536376072261-38c75010e6c9?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                                "Townhouse", 2, 2, Arrays.asList("Wifi", "Garden", "Historical charm", "City view")),

                        makeProperty("Romantic Windmill", "Amsterdam Countryside", 190.0, 4.9,
                                "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                                "Unique", 1, 1, Arrays.asList("Wifi", "Canalside", "Bicycle rental", "Breakfast")),

                        makeProperty("Luxury Penthouse", "Miami Beach, Florida", 500.0, 4.95,
                                "https://images.unsplash.com/photo-1502672023488-70e25813eb80?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                                "Penthouse", 3, 3, Arrays.asList("Rooftop pool", "Concierge", "Panoramic views", "Smart home"))
                );
                propertyRepository.saveAll(properties);
                System.out.println("Inserted sample properties: " + properties.size());
            }
        };
    }

    private static Property makeProperty(String title, String location, Double price, Double rating,
                                         String image, String type, int beds, int baths, List<String> amenities) {
        Property p = new Property();
        p.setTitle(title);
        p.setLocation(location);
        p.setPrice(price);
        p.setRating(rating);
        p.setImage(image);
        p.setType(type);
        p.setBeds(beds);
        p.setBaths(baths);
        p.setAmenities(amenities);
        return p;
    }
}
