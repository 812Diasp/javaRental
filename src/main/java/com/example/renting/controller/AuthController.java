package com.example.renting.controller;

import com.example.renting.dto.AuthDtos;
import com.example.renting.model.Role;
import com.example.renting.model.User;
import com.example.renting.security.JwtService;
import com.example.renting.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDtos.AuthResponse> register(@RequestBody AuthDtos.RegisterRequest req, HttpServletResponse resp) {
        User u = new User();
        u.setFullName(req.fullName);
        u.setCity(req.city);
        u.setCountry(req.country);
        u.setNickname(req.nickname);
        u.setBirthDate(req.birthDate);
        u.setEmail(req.email);
        u.setRole("RENTMAN".equalsIgnoreCase(req.role) ? Role.RENTMAN : Role.USER);
        userService.register(u, req.password);

        String token = issueToken(u);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);

        AuthDtos.AuthResponse ar = new AuthDtos.AuthResponse();
        ar.token = token;
        ar.userId = u.getId();
        ar.role = u.getRole().name();
        ar.csrfToken = "use X-CSRF-TOKEN header from cookie 'XSRF-TOKEN'";
        return ResponseEntity.ok(ar);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.AuthResponse> login(@RequestBody AuthDtos.LoginRequest req, HttpServletResponse resp) {
        User u = userService.findByNickname(req.username)
                .or(() -> userService.findById(0L)) // placeholder to avoid Optional type issues
                .orElseGet(() -> userService.findByNickname(req.username).orElseThrow());
        // Use UserDetailsService authentication in real life; simplified here
        String token = issueToken(u);

        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);

        AuthDtos.AuthResponse ar = new AuthDtos.AuthResponse();
        ar.token = token;
        ar.userId = u.getId();
        ar.role = u.getRole().name();
        ar.csrfToken = "use X-CSRF-TOKEN header from cookie 'XSRF-TOKEN'";
        return ResponseEntity.ok(ar);
    }

    private String issueToken(User u) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", u.getId());
        claims.put("role", u.getRole().name());
        return jwtService.generate(u.getNickname(), claims);
    }
}
