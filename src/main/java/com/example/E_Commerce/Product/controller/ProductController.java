package com.example.E_Commerce.Product.controller;

import com.example.E_Commerce.Product.model.Product;
import com.example.E_Commerce.Product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return productRepository.findAll(page, limit);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return productRepository.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productRepository.findByCategory(category);
    }

    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand) {
        return productRepository.findByBrand(brand);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productRepository.findByKeyword(keyword);
    }

    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @GetMapping("/in-stock")
    public List<Product> getInStockProducts() {
        return productRepository.findByInStock();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product productDetails) {
        return productRepository.findById(productId)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setCategory(productDetails.getCategory());
                    product.setStockQuantity(productDetails.getStockQuantity());
                    product.setBrand(productDetails.getBrand());
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStockQuantity(@PathVariable Long productId, @RequestParam int quantity) {
        return productRepository.findById(productId)
                .map(product -> {
                    product.setStockQuantity(quantity);
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return productRepository.findById(productId)
                .map(product -> {
                    productRepository.deleteById(productId);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
