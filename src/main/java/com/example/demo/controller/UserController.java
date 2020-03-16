package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/create")
    public String createUsers() {
        userService.createUsers();
        return "success";
    }

    @RequestMapping("/tocsv")
    public List<User> generateCSV() throws IOException {
        return userService.generateCSV();
    }

    @RequestMapping("/updateNames")
    public String updateNames() throws IOException {
        return "User Count: " + userService.updateUser();
    }
}
