package com.example.demo;

import com.example.demo.Entities.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    // until we setup jwt auth
    // Ensures we have a default user to attach transactions to
    @Bean
    CommandLineRunner initDatabase(UserRepository repository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new User("Demo User", "demo@example.com", passwordEncoder.encode("password")));
                System.out.println("Default user created! Login with email: demo@example.com, password: password");
            }
        };
    }
}
