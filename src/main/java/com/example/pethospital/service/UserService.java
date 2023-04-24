package com.example.pethospital.service;

import com.example.pethospital.pojo.User;

import java.util.List;

public interface UserService {
    User login(String userName, String password);
    int register(String userName, String password, String gender, int age);
    List<User> getAllUser();
    List<User> getUserByPage(int page, int size);
    User getUserById(int id);
    User getUserByName(String userName);
    void updatePassword(String userName, String password);
    void updateUserInformation(int id, String userName, int authority, String gender, int age);
    void deleteUser(int id);
}
