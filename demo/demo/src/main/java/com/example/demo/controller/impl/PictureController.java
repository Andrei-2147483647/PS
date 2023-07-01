package com.example.demo.controller.impl;

import ch.qos.logback.core.model.Model;
import com.example.demo.controller.IPictureController;
import com.example.demo.domain.Picture;
import com.example.demo.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@RestController
public class PictureController implements IPictureController {

    private final ProductService productService;

    @Autowired
    public PictureController(ProductService productService) {
        this.productService = productService;
    }

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @Override
    @GetMapping(path="/images/{id}")
    public ArrayList<Picture> getImagesIdsForProduct(@PathVariable int id) {
        return productService.getProductPictures(id);
    }

    @Override
    @PostMapping("/upload") public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
     //   model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        return "images/index";
    }
}
