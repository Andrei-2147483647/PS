package com.example.demo.controller;

import com.example.demo.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IUserController {
    @GetMapping
    List<User> getUsers();

    @PostMapping
    void registerUser(@RequestBody User user);

    @DeleteMapping(path = "/{id}")
    void deleteUser(@PathVariable int id);

    @PutMapping(path = "{id}")
    void updateUser(@PathVariable("id") int id, @RequestBody(required = false) String name, @RequestBody(required = false) String username, @RequestBody(required = false) String password, @RequestParam(required = false) String role);
}
