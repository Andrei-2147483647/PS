package com.example.demo.factory;

import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvExporter implements Exporter{

    @Override
    public byte[] export(List<Order> orders) {
        StringBuilder csvContent = new StringBuilder();

        // Create CSV header
        csvContent.append("Order ID,Customer Name,Total Amount\n");

        // Write order details
        for (Order order : orders) {
            StringBuilder s = new StringBuilder();
            for (Product product : OrderDAO.findOrderById(order.getId())) {
                s.append(product.getName());
                s.append(" - ");
                s.append(String.valueOf(product.getStock()));
            }
            csvContent.append(order.getId())
                    .append(",")
                    .append(order.getDate())
                    .append(",")
                    .append(s.toString())
                    .append("\n");
        }

        return csvContent.toString().getBytes(StandardCharsets.UTF_8);
    }

}
