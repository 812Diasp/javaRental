package com.example.renting.service;

import com.example.renting.model.User;
import com.example.renting.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(username);
        User user = byEmail.orElseGet(() -> userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
        if (user.isBlocked()) {
            throw new UsernameNotFoundException("User is blocked");
        }
        Collection<? extends GrantedAuthority> auth =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                user.getNickname(), user.getPassword(), auth);
    }

    @Transactional
    public User register(User user, String rawPassword) {
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }

    public User save(User user) { return userRepository.save(user); }

    public Optional<User> findById(Long id) { return userRepository.findById(id); }

    public Optional<User> findByNickname(String nickname) { return userRepository.findByNickname(nickname); }
}
