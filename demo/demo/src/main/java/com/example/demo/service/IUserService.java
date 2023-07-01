package com.example.demo.service;

import com.example.demo.domain.User;

import java.util.ArrayList;

public interface IUserService {
    String encryptPassword(String password);

    User findUserById(int id);

    User findUserByUsername(String username);

    int insertUser(User user);

    int editUser(int id, String newName, String newUsername, String newPassword, String newRole);

    int deleteUser(int id);

    ArrayList<User> getAllUsers();
}
