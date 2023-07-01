package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;

import java.util.ArrayList;
import java.util.List;

public interface IOrderService {
    void insertOrder(Order order, ArrayList<Integer> productIds, ArrayList<Integer> quantities);

    List<Product> getOrderAndItsProducts(int orderId);

    Order getOrder(int orderId);

    void deleteOrder(int orderId);

    List<Order> getAllOrders();

    List<Order> getOrdersBetween2Dates(String date1, String date2);

    Product findMostOrderedProduct(String date1, String date2);

    List<Product> findMostOrderedNProducts(String date1, String date2, int n);
}
