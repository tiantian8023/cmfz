package com.mp.cmfz.controller;

import com.mp.cmfz.entity.Chapter;
import com.mp.cmfz.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/findAllChapter")
    public ResponseEntity<Map<String, Object>> findAllChapter(Integer page, Integer rows, String albumId) {
        return ResponseEntity.ok(chapterService.findAllChapter(page, rows, albumId));
    }

    @RequestMapping("/addChapter")
    public Map<String, Object> addChapter(Chapter chapter, String oper) {
        System.out.println(chapter);
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            try {
                String id = add(chapter);
                map.put("code", 200);
                map.put("data", id);
            } catch (Exception e) {
                map.put("code", 500);
                map.put("msg", e.getMessage());
            }
        } else if ("edit".equals(oper)) {
            chapterService.updateChapter(chapter);
        } else {
            System.out.println(chapter);
            chapterService.deleteChapter(chapter);
        }
        return map;
    }


    private String add(Chapter chapter) {
        System.out.println("chapter.getAlbumId() = " + chapter.getAlbumId());
        String id = chapterService.addChapter(chapter);
        return id;
    }

    @RequestMapping("/upload")
    public void upload(MultipartFile audioPath, String id, HttpServletRequest request) throws IOException {
        System.out.println("id = " + id);
        chapterService.upload(audioPath, id, request);
    }
}
