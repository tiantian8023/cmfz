package com.mp.cmfz.service;

import com.mp.cmfz.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AdminService {
    List<Admin> findAllAdmin();

    String login(Admin admin, String enCode, HttpSession session);
}
