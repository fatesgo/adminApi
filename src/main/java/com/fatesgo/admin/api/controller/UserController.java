package com.fatesgo.admin.api.controller;

import com.fatesgo.admin.api.mapper.UserMapper;
import com.fatesgo.admin.api.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired(required = false)
    private  UserMapper userMapper;
    @GetMapping("/user/{id}")
    public User queryUser(@PathVariable("id") int id) {
        User user = userMapper.queryUserById(id);
        return user;
    }



}
