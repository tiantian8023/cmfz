package com.mp.cmfz.controller;

import com.mp.cmfz.entity.Admin;
import com.mp.cmfz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Admin> findAllAdmin() {
        List<Admin> admins = adminService.findAllAdmin();
        return admins;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String adminLogin(HttpServletRequest request, String username, String password, String enCode, HttpSession session) {
        Admin admin = new Admin();
        admin.setName(username).setPassword(password);
        String msg = adminService.login(admin, enCode, session);
        request.setAttribute("msg", msg);
        return msg;
    }
}
