package com.example.demo.service.impl;

import com.example.demo.DAO.UserDAO;
import com.example.demo.domain.User;
import com.example.demo.service.IUserService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class UserService implements IUserService {

    @Override
    public String encryptPassword(String password) {
        String encryptedpassword = null;
        try
        {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedpassword;
    }

    @Override
    public User findUserById(int id) {
        User user = UserDAO.findById(id);
        if(user == null) {
            throw new NoSuchElementException("The user with id =" + id + " was not found!");
        }
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = UserDAO.findByUsername(username);
        if(user == null) {
            throw new NoSuchElementException("The user with username =" + username + " was not found!");
        }
        return user;
    }

    @Override
    public int insertUser(User user) {
        return UserDAO.insert(user);
    }


    @Override
    public int editUser(int id, String newName, String newUsername, String newPassword, String newRole) {
        User user = new User(id,newName,newUsername, newPassword, newRole);
        return UserDAO.edit(id,newName,newUsername, newPassword, newRole);
    }

    @Override
    public int deleteUser(int id) {
        return UserDAO.delete(id);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return UserDAO.allUsers();
    }
}
