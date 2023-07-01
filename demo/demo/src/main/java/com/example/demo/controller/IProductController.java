package com.example.demo.controller;

import com.example.demo.domain.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IProductController {
    @GetMapping
    List<Product> getProducts();

    @PostMapping
    void registerProduct(@RequestBody Product product);

    @DeleteMapping(path = "/{id}")
    void deleteProduct(@PathVariable int id);

    @PutMapping(path = "{id}")
    void updateProduct(@PathVariable("id") int id, @RequestBody(required = false) String name, @RequestBody(required = false) double price, @RequestBody(required = false) int stock);
}
