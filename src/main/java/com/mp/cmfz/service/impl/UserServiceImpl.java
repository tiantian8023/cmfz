package com.mp.cmfz.service.impl;

import com.mp.cmfz.dao.UserDao;
import com.mp.cmfz.entity.ProvinceDto;
import com.mp.cmfz.entity.User;
import com.mp.cmfz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAllUser() {
        return userDao.selectAll();
    }

    @Override
    public Map<String, Object> findUserByRegisterTime() {
        Map<String, Object> map = new HashMap<>();
        int firstWeek = 7;
        List<Integer> list = new ArrayList<>();
        int count1 = userDao.findUserByRegisterTime(0, firstWeek);
        map.put("nan1", count1);
        int twoWeek = 14;
        int count2 = userDao.findUserByRegisterTime(firstWeek, twoWeek);
        map.put("nan2", count2);
        int threeWeek = 21;
        int count3 = userDao.findUserByRegisterTime(twoWeek, threeWeek);
        map.put("nan3", count3);
        return map;
    }

    @Override
    public int addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public Map<String, Object> findUserCountByMap() {
        Map<String, Object> map = new HashMap<>();
        List<ProvinceDto> nanlist = userDao.findUserCountByMap("男");
        map.put("nan", nanlist);
        List<ProvinceDto> nvlist = userDao.findUserCountByMap("女");
        map.put("nv", nvlist);
        return map;
    }
}
