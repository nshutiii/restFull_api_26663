package com.example.E_Commerce.Product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long productId;

    private String name;
    private String description;
    private Double price;
    private String category;
    private int stockQuantity;
    private String brand;
}
