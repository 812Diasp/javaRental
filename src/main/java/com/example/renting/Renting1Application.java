package com.example.renting;

import com.example.renting.model.Role;
import com.example.renting.model.User;
import com.example.renting.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Renting1Application {

    public static void main(String[] args) {
        SpringApplication.run(Renting1Application.class, args);
    }
    @Bean
    CommandLineRunner initUsers(UserService userService) {
        return args -> {
            // Создаем тестового пользователя если его нет
            if (userService.findByNickname("admin").isEmpty()) {
                User admin = new User();
                admin.setNickname("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword("temp_password"); // будет перезаписан при регистрации
                admin.setRole(Role.ADMIN);
                admin.setFullName("Admin User");
                admin.setCity("Moscow");
                admin.setCountry("Russia");
                admin.setBlocked(false);

                userService.register(admin, "admin123");
                System.out.println("Created admin user: admin / admin123");
            }

            // Можно добавить тестового обычного пользователя
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
        };
    }
}
