package com.example.Restaurant.Menu.controller;

import com.example.Restaurant.Menu.model.MenuItem;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuController() {
        // Initialize with 8 menu items
        menuItems.add(new MenuItem(1L, "Spring Rolls", "Crispy vegetable spring rolls", 5.99, "Appetizer", true));
        menuItems.add(
                new MenuItem(2L, "Chicken Wings", "Spicy buffalo wings with blue cheese dip", 8.50, "Appetizer", true));
        menuItems.add(new MenuItem(3L, "Grilled Salmon", "Fresh salmon with asparagus and mashed potatoes", 18.99,
                "Main Course", true));
        menuItems.add(new MenuItem(4L, "Beef Burger", "Juicy beef patty with lettuce, tomato, and cheese", 12.50,
                "Main Course", true));
        menuItems.add(new MenuItem(5L, "Pasta Carbonara", "Classic Italian pasta with eggs, cheese, and pancetta",
                14.00, "Main Course", false));
        menuItems.add(new MenuItem(6L, "Chocolate Lava Cake", "Warm chocolate cake with a gooey center", 6.99,
                "Dessert", true));
        menuItems.add(new MenuItem(7L, "Apple Pie", "Traditional apple pie served with vanilla ice cream", 5.50,
                "Dessert", true));
        menuItems.add(new MenuItem(8L, "Lemonade", "Freshly squeezed lemonade with mint", 3.00, "Beverage", true));
    }

    // GET /api/menu - Get all menu items
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    // GET /api/menu/{id} - Get specific menu item
    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // GET /api/menu/category/{category} - Get items by category
    @GetMapping("/category/{category}")
    public List<MenuItem> getMenuItemsByCategory(@PathVariable String category) {
        return menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // GET /api/menu/available - Get only available items
    @GetMapping("/available")
    public List<MenuItem> getAvailableMenuItems(@RequestParam(defaultValue = "true") boolean available) {
        return menuItems.stream()
                .filter(item -> item.isAvailable() == available)
                .collect(Collectors.toList());
    }

    // GET /api/menu/search?name={name} - Search menu items by name
    @GetMapping("/search")
    public List<MenuItem> searchMenuItemsByName(@RequestParam String name) {
        return menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // POST /api/menu - Add new menu item
    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem newItem) {
        if (newItem.getId() == null) {
            long nextId = menuItems.stream().mapToLong(MenuItem::getId).max().orElse(0L) + 1;
            newItem.setId(nextId);
        }
        menuItems.add(newItem);
        return newItem;
    }

    // PUT /api/menu/{id}/availability - Toggle item availability
    @PutMapping("/{id}/availability")
    public MenuItem toggleAvailability(@PathVariable Long id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                item.setAvailable(!item.isAvailable());
                return item;
            }
        }
        return null;
    }

    // DELETE /api/menu/{id} - Remove menu item
    @DeleteMapping("/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));
        return removed ? "Item removed successfully" : "Item not found";
    }
}
