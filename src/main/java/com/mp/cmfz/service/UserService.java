package com.mp.cmfz.service;

import com.mp.cmfz.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAllUser();

    Map<String, Object> findUserByRegisterTime();

    int addUser(User user);

    Map<String, Object> findUserCountByMap();
}
