package com.example.demo.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Order {
    private int id;
    private String date;

    private double price;

    private Status status;


    public Order() {
    }

    public Order(int id, String date, Status status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    public Order(String date, Status status) {
        this.date = date;
        this.status = status;
    }

    public Order(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public Order(int id, String date, double price) {
        this.id = id;
        this.date = date;
        this.price = price;
    }

    public Order(String date, double price) {
        this.date = date;
        this.price = price;
    }

    public Order(String date) {
        this.date = date;
        this.status = Status.NEW_ORDER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }


    public void computePrice(ArrayList<Product> products) {
        double totalPrice = 0.0;
        for(Product p : products) {
            totalPrice += p.getPrice();
        }
        this.price = totalPrice;
    }
}
