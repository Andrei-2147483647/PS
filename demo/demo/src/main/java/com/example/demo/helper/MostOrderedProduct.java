package com.example.demo.helper;

import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;

import java.util.*;

public class MostOrderedProduct {
    public static Product findMostFrequentProduct(List<Order> orders) {
        Map<Product, Integer> productCountMap = new HashMap<>();

        for (Order order : orders) {
            for (Product product : OrderDAO.findOrderById(order.getId())) {
                productCountMap.put(product, productCountMap.getOrDefault(product, 0) + 1);
            }
        }

        Product mostFrequentProduct = null;
        int maxCount = 0;

        for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentProduct = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostFrequentProduct;
    }

    public static List<Product> findMostFrequentNProducts(List<Order> orders, int n) {
        Map<Integer, Integer> productCountMap = new HashMap<>();
        for (Order order : orders) {
            List<Product> products = OrderDAO.findOrderById(order.getId());
            for (Product product : products) {
                int productId = product.getId();
                productCountMap.put(productId, productCountMap.getOrDefault(productId, 0) + 1);
            }
        }

        List<Map.Entry<Integer, Integer>> productCountList = new ArrayList<>(productCountMap.entrySet());
        productCountList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<Product> topNProducts = new ArrayList<>();
        for (int i = 0; i < n && i < productCountList.size(); i++) {
            int productId = productCountList.get(i).getKey();
            Product product = ProductDAO.findById(productId);
            topNProducts.add(product);
        }

        return topNProducts;
    }
}
