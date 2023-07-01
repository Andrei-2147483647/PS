package com.example.demo.controller.impl;

import com.example.demo.controller.IPublicAPIController;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/public")
public class PublicAPIController implements IPublicAPIController {

    private final OrderService orderService;

    @Autowired
    public PublicAPIController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @PostMapping
    public void registerOrder(@RequestBody Order order, @RequestBody ArrayList<Integer> productIds, @RequestBody ArrayList<Integer> quantities) {
        orderService.insertOrder(order,productIds,quantities);
    }

    @Override
    @GetMapping("/orders")
    public List<Order> getOrdersBetweenDates(@RequestParam("startDate") String startDate,
                                             @RequestParam("endDate") String endDate) {
        return orderService.getOrdersBetween2Dates(startDate, endDate);
    }

    @Override
    @GetMapping("/most-ordered-product")
    public Product getMostOrderedProductBetweenDates(@RequestParam("startDate") String startDate,
                                                     @RequestParam("endDate") String endDate) {
        return orderService.findMostOrderedProduct(startDate, endDate);
    }

    @Override
    @GetMapping("/top-10-most-ordered-products")
    public List<Product> getTop10MostOrderedProducts(@RequestParam("startDate") String startDate,
                                                     @RequestParam("endDate") String endDate) {
        return orderService.findMostOrderedNProducts(startDate, endDate, 10);
    }

}
