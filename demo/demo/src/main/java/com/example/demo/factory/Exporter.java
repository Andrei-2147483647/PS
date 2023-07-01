package com.example.demo.factory;

import com.example.demo.domain.Order;

import java.util.List;

public interface Exporter {
    byte[] export(List<Order> orders);
}
