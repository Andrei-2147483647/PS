package com.example.demo.controller.impl;

import com.example.demo.controller.IProductController;
import com.example.demo.domain.Product;
import com.example.demo.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/product")
@CrossOrigin("http://localhost:3000")
public class ProductController implements IProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @Override
    @PostMapping
    public void registerProduct(@RequestBody Product product) {
        productService.insertProduct(product);
    }

    @Override
    @DeleteMapping(path="/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @Override
    @PutMapping(path="{id}")
    public void updateProduct(@PathVariable("id") int id, @RequestBody(required = false) String name, @RequestBody(required = false) double price, @RequestBody(required = false) int stock) {
        productService.editProduct(id,name,price,stock);
    }
}
