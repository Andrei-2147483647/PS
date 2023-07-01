package com.example.demo.controller;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public interface IPublicAPIController {
    @PostMapping
    void registerOrder(@RequestBody Order order, @RequestBody ArrayList<Integer> productIds, @RequestBody ArrayList<Integer> quantities);

    @GetMapping("/orders")
    List<Order> getOrdersBetweenDates(@RequestParam("startDate") String startDate,
                                      @RequestParam("endDate") String endDate);

    @GetMapping("/most-ordered-product")
    Product getMostOrderedProductBetweenDates(@RequestParam("startDate") String startDate,
                                              @RequestParam("endDate") String endDate);

    @GetMapping("/top-10-most-ordered-products")
    List<Product> getTop10MostOrderedProducts(@RequestParam("startDate") String startDate,
                                              @RequestParam("endDate") String endDate);
}
