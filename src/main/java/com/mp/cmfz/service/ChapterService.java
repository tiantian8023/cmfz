package com.mp.cmfz.service;

import com.mp.cmfz.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface ChapterService {
    Map<String, Object> findAllChapter(Integer page, Integer rows, String albumId);

    String updateChapter(Chapter chapter);

    void deleteChapter(Chapter chapter);

    String addChapter(Chapter chapter);

    void upload(MultipartFile audioPath, String id, HttpServletRequest request) throws IOException;
}
