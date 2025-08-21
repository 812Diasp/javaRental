package com.example.renting.dto;

import java.time.LocalDate;

public class AuthDtos {
    public static class RegisterRequest {
        public String fullName;
        public String city;
        public String country;
        public String nickname;
        public String password;
        public LocalDate birthDate;
        public String email;
        public String role; // USER or RENTMAN
    }

    public static class LoginRequest {
        public String username; // nickname or email
        public String password;
    }

    public static class AuthResponse {
        public String token;
        public String csrfToken;
        public Long userId;
        public String role;
    }
}
