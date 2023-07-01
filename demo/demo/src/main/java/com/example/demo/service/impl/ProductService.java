package com.example.demo.service.impl;

import com.example.demo.DAO.PictureDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.domain.Picture;
import com.example.demo.domain.Product;
import com.example.demo.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class ProductService implements IProductService {
    @Override
    public Product findProductById(int id) {
        Product product = ProductDAO.findById(id);
        if(product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }

    @Override
    public int insertProduct(Product product) {
        return ProductDAO.insert(product);
    }

    @Override
    public int editProduct(int id, String newName, double newPrice, int newStock) {
        return ProductDAO.edit(id,newName,newPrice,newStock);
    }

    @Override
    public int editProduct(int id, String newName, double newPrice, int newStock, ArrayList<Picture> pictures) {
        for(Picture p : pictures)
            PictureDAO.insert(p);
        return ProductDAO.edit(id,newName,newPrice,newStock);
    }

    @Override
    public int deleteProduct(int id) {
        return ProductDAO.delete(id);
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return ProductDAO.allProducts();
    }

    @Override
    public void insertProduct(Product product, ArrayList<Picture> pictures) {
        ProductDAO.insert(product);
        for(Picture p : pictures)
            PictureDAO.insert(p);
    }

    @Override
    public ArrayList<Picture> getProductPictures(int id) {
        return PictureDAO.getProductPictures(id);
    }
}
