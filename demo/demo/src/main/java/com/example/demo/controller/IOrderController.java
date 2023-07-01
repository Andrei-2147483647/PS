package com.example.demo.controller;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public interface IOrderController {
    @GetMapping
    List<Order> getOrders();

    @GetMapping(path = "{id}")
    List<Product> getProductsFromOrder(@PathVariable int id);

    @PostMapping
    void registerOrder(@RequestBody Order order, @RequestBody ArrayList<Integer> productIds, @RequestBody ArrayList<Integer> quantities);

    @DeleteMapping(path = "{id}")
    void deleteOrder(@PathVariable int id);

    @GetMapping("/orders-export")
    ResponseEntity<Resource> exportOrders(@RequestParam String format);
}
