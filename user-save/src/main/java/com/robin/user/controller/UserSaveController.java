package com.robin.user.controller;

import com.robin.entity.Users;
import com.robin.user.service.UserSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserSaveController {

    @Autowired
    private UserSaveService userSaveService;

    @PostMapping("/save")
    public int save(@RequestBody Users user){
        return userSaveService.save(user);
    }
}
