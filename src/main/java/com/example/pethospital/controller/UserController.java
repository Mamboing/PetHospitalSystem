package com.example.pethospital.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.User;
import com.example.pethospital.service.UserService;
import com.example.pethospital.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/getAll")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @RequestMapping("/getByPage")
    public List<User> getUserByPage(int page, int size){
        if(page < 0 || size <= 0){
            return new ArrayList<>();
        }
        return userService.getUserByPage(page, size);
    }

    @RequestMapping("/me")
    public MessageBean<?> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        if(user != null){
            String msg = "Success";
            return new MessageBean<>(MessageCodeEnum.OK, user, msg);
        }
        else{
            return new MessageBean<>(MessageCodeEnum.NO, "用户不存在");
        }
    }

    @PostMapping("/login")
    public MessageBean<?> login(@RequestParam("userName") String userName, @RequestParam("password") String password){
        if(userName == null || password == null){
            return new MessageBean<>(MessageCodeEnum.INVALID_PARAMS);
        }
        JSONObject data = new JSONObject();
        User user = userService.login(userName, password);
        if(user != null){
            String token = JWTUtil.generateToken(user);
            String msg = "Success";
            data.put("Authorization", token);
            data.put("AuthorityValue", user.getAuthority());
            return new MessageBean<>(MessageCodeEnum.OK, data, msg);
        }
        else{
            String msg = "用户名或密码错误！";
            return new MessageBean<>(MessageCodeEnum.PASSWORD_WRONG, msg);
        }
    }

    @PostMapping("/register")
    public MessageBean<?> register(@RequestParam String userName, @RequestParam String password, @RequestParam String gender, @RequestParam int age){
        if(userName == null || password == null || gender == null || age <= 0){
            return new MessageBean<>(MessageCodeEnum.INVALID_PARAMS);
        }
        int ok = userService.register(userName, password, gender, age);
        if(ok == 0) {
            String msg = "username exists";
            return new MessageBean<>(MessageCodeEnum.NO, msg);
        }
        else{
            String msg = "注册成功";
            return new MessageBean<>(MessageCodeEnum.OK, msg);
        }
    }

    @PostMapping("/changePassword")
    public MessageBean<?> modifyPassword(@RequestParam String originPassword, @RequestParam String newPassword){
        if(originPassword == null || newPassword == null){
            return new MessageBean<>(MessageCodeEnum.INVALID_PARAMS);
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.login(userName, originPassword);
        if(user != null){
            userService.updatePassword(userName, newPassword);
            String msg = "修改成功";
            return new MessageBean<>(MessageCodeEnum.OK, msg);
        }
        else{
            String msg = "wrong password!";
            return new MessageBean<>(MessageCodeEnum.PASSWORD_WRONG, msg);
        }
    }

    @PostMapping("/updateInfo")
    public MessageBean<?> updateInformation(@RequestParam int id, @RequestParam String userName, @RequestParam int authority, @RequestParam String gender, @RequestParam int age){
        if(id <= 0 || userName == null || gender == null || age <= 0){
            return new MessageBean<>(MessageCodeEnum.INVALID_PARAMS);
        }
        if(authority != 1 && authority != 3 && authority != 5){
            return new MessageBean<>(MessageCodeEnum.INVALID_PARAMS, "权限设置有误");
        }
        userService.updateUserInformation(id, userName, authority, gender, age);
        User user = userService.getUserById(id);
        String msg = "修改成功！";
        return new MessageBean<>(MessageCodeEnum.OK, user, msg);
    }

    @PostMapping("/delete/{id}")
    public MessageBean<?> deleteUser(@PathVariable int id){
        if(id <= 0){
            return new MessageBean<>(MessageCodeEnum.INVALID_PARAMS);
        }
        userService.deleteUser(id);
        return new MessageBean<>(MessageCodeEnum.OK, "删除成功");
    }
}
