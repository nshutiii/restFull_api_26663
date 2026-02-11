package com.example.E_Commerce.Product.config;

import com.example.E_Commerce.Product.model.Product;
import com.example.E_Commerce.Product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

        @Bean
        CommandLineRunner initDatabase(ProductRepository repository) {
                return args -> {
                        repository.save(new Product(null, "iPhone 15", "Apple's latest flagship smartphone", 999.99,
                                        "Electronics", 50, "Apple"));
                        repository.save(new Product(null, "Samsung Galaxy S24", "High-end Android smartphone", 899.99,
                                        "Electronics", 45, "Samsung"));
                        repository.save(new Product(null, "MacBook Pro 14", "Powerful laptop for professionals",
                                        1999.99, "Computers", 20, "Apple"));
                        repository.save(new Product(null, "Dell XPS 15", "Premium Windows laptop", 1499.99, "Computers",
                                        15, "Dell"));
                        repository.save(new Product(null, "Sony WH-1000XM5",
                                        "Industry-leading noise cancelling headphones", 349.99, "Accessories", 30,
                                        "Sony"));
                        repository.save(new Product(null, "Logitech MX Master 3S",
                                        "Advanced wireless productivity mouse", 99.99, "Accessories", 100, "Logitech"));
                        repository.save(new Product(null, "Nintendo Switch OLED", "Popular hybrid gaming console",
                                        349.99, "Gaming", 25, "Nintendo"));
                        repository.save(new Product(null, "Sony PlayStation 5", "Next-gen gaming console", 499.99,
                                        "Gaming", 10, "Sony"));
                        repository.save(new Product(null, "Bose QuietComfort Ultra", "Premium over-ear headphones",
                                        429.99, "Accessories", 0, "Bose"));
                        repository.save(new Product(null, "Amazon Echo Dot (5th Gen)", "Smart speaker with Alexa",
                                        49.99, "Smart Home", 60, "Amazon"));
                        repository.save(new Product(null, "Google Pixel 8", "Google's smart and capable phone", 699.99,
                                        "Electronics", 35, "Google"));

                        System.out.println("Sample products loaded into the in-memory repository.");
                };
        }
}
