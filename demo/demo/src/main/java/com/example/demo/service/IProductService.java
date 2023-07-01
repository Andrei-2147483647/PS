package com.example.demo.service;

import com.example.demo.domain.Picture;
import com.example.demo.domain.Product;

import java.util.ArrayList;

public interface IProductService {
    Product findProductById(int id);

    int insertProduct(Product product);

    int editProduct(int id, String newName, double newPrice, int newStock);

    int editProduct(int id, String newName, double newPrice, int newStock, ArrayList<Picture> pictures);

    int deleteProduct(int id);

    ArrayList<Product> getAllProducts();

    void insertProduct(Product product, ArrayList<Picture> pictures);

    ArrayList<Picture> getProductPictures(int id);
}
