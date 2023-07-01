package com.example.demo.domain;

import org.springframework.web.multipart.MultipartFile;

public class Picture {
    int id;
    String title;

    int productId;

    public Picture(int id, String title, int productId) {
        this.id = id;
        this.title = title;
        this.productId = productId;
    }

    public Picture(String title, int productId) {
        this.title = title;
        this.productId = productId;
    }

    public Picture() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
