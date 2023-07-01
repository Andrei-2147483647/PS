package com.example.demo.controller.impl;

import com.example.demo.controller.IUserController;
import com.example.demo.domain.User;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class UserController implements IUserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @Override
    @PostMapping
    public void registerUser(@RequestBody User user) {
        userService.insertUser(user);
    }

    @Override
    @DeleteMapping(path="/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @Override
    @PutMapping(path = "{id}")
    public void updateUser(@PathVariable("id") int id, @RequestBody(required = false) String name, @RequestBody(required = false) String username, @RequestBody(required = false) String password, @RequestParam(required = false) String role) {
        userService.editUser(id,name,username,password,role);
    }
}
