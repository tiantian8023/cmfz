package com.mp.cmfz.controller;

import com.mp.cmfz.entity.User;
import com.mp.cmfz.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUser")
    public Map<String, Object> findAllUser() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<User> users = userService.findAllUser();
            map.put("code", 200);
            map.put("data", users);
        } catch (Exception e) {
            map.put("code", 200);
            map.put("data", e.getMessage());
        }
        int[] a = {2, 6, 3, 9, 48};
        map.put("data", a);
        return map;
    }

    @RequestMapping("/findUserByRegisterTime")
    public Map<String, Object> findUserByRegisterTime() {
        return userService.findUserByRegisterTime();
    }

    @RequestMapping("/findUserCountByMap")
    public Map<String, Object> findUserCountByMap() {
        Map<String, Object> map = userService.findUserCountByMap();
        return map;
    }

    @RequestMapping("/addUser")
    public void addUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString())
                .setCreateDate(new Date());
        int i = userService.addUser(user);
        if (i == 1) {
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-0f2ac1f445814d008f250eb9eb54cf3c");
            goEasy.publish("176_mp", "添加用户成功");
        }
    }
}
