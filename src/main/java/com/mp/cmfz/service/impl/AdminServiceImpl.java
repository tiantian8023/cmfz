package com.mp.cmfz.service.impl;

import com.mp.cmfz.dao.AdminDao;
import com.mp.cmfz.entity.Admin;
import com.mp.cmfz.service.AdminService;
import com.mp.cmfz.utils.Md5UUIDSaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public List<Admin> findAllAdmin() {
        return adminDao.selectAll();
    }

    @Override
    public String login(Admin admin, String enCode, HttpSession session) {
        String code = (String) session.getAttribute("code");
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(enCode)) {
            return "验证码不能为空";
        }
        if (!enCode.equalsIgnoreCase(code)) {
            return "验证码错误";
        }
        System.out.println("admin = " + admin);
        Admin login = new Admin();
        login.setName(admin.getName());
        Admin loginAdmin = adminDao.selectOne(login);
        if (loginAdmin == null) {
            return "用户名错误";
        } else {
            String salt = loginAdmin.getSalt();
            String md5Code = Md5UUIDSaltUtil.createMd5Code(salt + admin.getPassword());
            if (!md5Code.equals(loginAdmin.getPassword())) {
                return "用户名或密码错误";
            } else {
                session.setAttribute("admin", loginAdmin);
                return "success";
            }
        }
    }
}
