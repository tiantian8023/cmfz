package com.mp.cmfz.service;

import com.mp.cmfz.entity.Slide;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface SlideService {
    Map<String, Object> findAllSlide(Integer page, Integer rows);

    String addSlide(Slide slide);

    void deleteSlide(Slide slide);

    String updateSlide(Slide slide);

    void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException;

    void exportExcel();
}
