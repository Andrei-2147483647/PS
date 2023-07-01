package com.example.demo.controller;

import ch.qos.logback.core.model.Model;
import com.example.demo.domain.Picture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

public interface IPictureController {
    @GetMapping(path = "/images/{id}")
    ArrayList<Picture> getImagesIdsForProduct(@PathVariable int id);

    @PostMapping("/upload")
    String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException;
}
