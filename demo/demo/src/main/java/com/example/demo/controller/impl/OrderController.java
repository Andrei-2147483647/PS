package com.example.demo.controller.impl;

import com.example.demo.controller.IOrderController;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.factory.Exporter;
import com.example.demo.factory.ExporterFactory;
import com.example.demo.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.ArrayList;


@RestController
@RequestMapping(path = "api/order")
public class OrderController implements IOrderController {
    private final OrderService orderService;
    private final ExporterFactory exporterFactory;

    @Autowired
    public OrderController(OrderService orderService, ExporterFactory exporterFactory) {
        this.orderService = orderService;
        this.exporterFactory = exporterFactory;
    }

    @Override
    @GetMapping
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @Override
    @GetMapping(path="{id}")
    public List<Product> getProductsFromOrder(@PathVariable int id) {
        return orderService.getOrderAndItsProducts(id);
    }

    @Override
    @PostMapping
    public void registerOrder(@RequestBody Order order, @RequestBody ArrayList<Integer> productIds, @RequestBody ArrayList<Integer> quantities) {
        orderService.insertOrder(order,productIds,quantities);
    }

    @Override
    @DeleteMapping(path="{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }


    @Override
    @GetMapping("/orders-export")
    public ResponseEntity<Resource> exportOrders(@RequestParam String format) {
        List<Order> orders = orderService.getAllOrders();

        Exporter exporter = exporterFactory.createExporter(format);

        byte[] exportData = exporter.export(orders);

        ByteArrayResource resource = new ByteArrayResource(exportData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders." + format);


        MediaType contentType;
        if (format.equalsIgnoreCase("csv")) {
            contentType = MediaType.parseMediaType("text/csv");
        } else if (format.equalsIgnoreCase("excel")) {
            contentType = MediaType.parseMediaType("application/vnd.ms-excel");
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(contentType)
                .body(resource);
    }
}
