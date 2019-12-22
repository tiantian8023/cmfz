package com.mp.cmfz.controller;

import com.mp.cmfz.utils.CreateValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Controller
@RequestMapping("/validate")
public class ValidateController {

    @RequestMapping("/code")
    public void getImageCode(HttpSession session, HttpServletResponse response) {
        CreateValidateCode validateCode = CreateValidateCode.Instance();
        String code = validateCode.getString();
        System.out.println("验证码:" + code);
        session.setAttribute("code", code);
        BufferedImage image = validateCode.getImage();
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/png");
            ImageIO.write(image, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
