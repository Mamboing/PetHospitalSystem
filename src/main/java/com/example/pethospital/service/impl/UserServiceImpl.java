package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.UserMapper;
import com.example.pethospital.pojo.User;
import com.example.pethospital.service.UserService;
import com.example.pethospital.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String userName, String password) {
        String md5 = MD5Util.MD5Encrypt(password);
        User user = userMapper.selectUserByPassword(userName, md5);
        return user;
    }

    @Override
    public int register(String userName, String password, String gender, int age) {
        int count = userMapper.selectGivenUserCount(userName);
        if(count != 0){
            return 0;
        }
        User newUser = new User();
        String md5 = MD5Util.MD5Encrypt(password);
        newUser.setUserName(userName);
        newUser.setPassword(md5);
        newUser.setGender(gender);
        newUser.setAuthority(1);
        newUser.setAge(age);
        return userMapper.addUser(newUser);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public List<User> getUserByPage(int page, int size) {
        page = (page - 1) * size;
        return userMapper.selectUserByPage(page, size);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public User getUserByName(String userName) {
        return userMapper.selectUserByName(userName);
    }

    @Override
    public void updatePassword(String userName, String password) {
        userMapper.updateUserPassword(userName, password);
    }

    @Override
    public void updateUserInformation(int id, String userName, int authority, String gender, int age) {
        userMapper.updateUserInformation(id, userName, authority, gender, age);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteUserById(id);
    }
}
