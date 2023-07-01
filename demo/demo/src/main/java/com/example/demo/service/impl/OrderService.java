package com.example.demo.service.impl;

import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.helper.DateFormat;
import com.example.demo.helper.MostOrderedProduct;
import com.example.demo.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Override
    public void insertOrder(Order order, ArrayList<Integer> productIds, ArrayList<Integer> quantities) {
        int id = OrderDAO.insert(order);
        for (int i = 0; i < productIds.size(); i++) {
            Product p = ProductDAO.findById(i);
            if (p.getStock() >= quantities.get(i)) {
                OrderDAO.insertOrderProduct(id, productIds.get(i), quantities.get(i));
                ProductDAO.edit(productIds.get(i), p.getName(), p.getPrice(), p.getStock() - quantities.get(i));
            }
        }
    }

    @Override
    public List<Product> getOrderAndItsProducts(int orderId) {
        ArrayList<Product> products = OrderDAO.findOrderById(orderId);
        OrderDAO.findById(orderId);
        return products;
    }

    @Override
    public Order getOrder(int orderId) {
        return OrderDAO.findById(orderId);
    }

    @Override
    public void deleteOrder(int orderId) {
        OrderDAO.delete(orderId);
    }


    @Override
    public List<Order> getAllOrders() {
        return OrderDAO.allOrders();
    }

    @Override
    public List<Order> getOrdersBetween2Dates(String date1, String date2) {
        List<Order> orders = OrderDAO.allOrders();
        for(Order o : orders) {
            if(!DateFormat.isDateInRange(o.getDate(),date1,date2)) {
                orders.remove(o);
            }
        }
        return orders;
    }

    @Override
    public Product findMostOrderedProduct(String date1, String date2) {
        List<Order> orders = OrderDAO.allOrders();
        for(Order o : orders) {
            if(!DateFormat.isDateInRange(o.getDate(),date1,date2)) {
                orders.remove(o);
            }
        }
        return MostOrderedProduct.findMostFrequentProduct(orders);
    }

    @Override
    public List<Product> findMostOrderedNProducts(String date1, String date2, int n) {
        List<Order> orders = OrderDAO.allOrders();
        for(Order o : orders) {
            if(!DateFormat.isDateInRange(o.getDate(),date1,date2)) {
                orders.remove(o);
            }
        }

        return MostOrderedProduct.findMostFrequentNProducts(orders,n);
    }
}
