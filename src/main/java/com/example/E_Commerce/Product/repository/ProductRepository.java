package com.example.E_Commerce.Product.repository;

import com.example.E_Commerce.Product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public List<Product> findAll(int page, int limit) {
        return products.values().stream()
                .skip((long) page * limit)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findByCategory(String category) {
        return products.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> findByBrand(String brand) {
        return products.values().stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    public List<Product> findByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword) ||
                        p.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public List<Product> findByPriceBetween(Double min, Double max) {
        return products.values().stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public List<Product> findByInStock() {
        return products.values().stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }

    public Product save(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(idGenerator.getAndIncrement());
        }
        products.put(product.getProductId(), product);
        return product;
    }

    public void deleteById(Long id) {
        products.remove(id);
    }
}
