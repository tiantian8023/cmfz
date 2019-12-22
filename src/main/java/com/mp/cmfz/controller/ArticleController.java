package com.mp.cmfz.controller;

import com.mp.cmfz.dto.Photo;
import com.mp.cmfz.entity.Article;
import com.mp.cmfz.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    addAriticle


    @RequestMapping("/addAriticle")
    public void addAriticle(String oper, Article article) {
        if ("add".equals(oper)) {
            articleService.addAriticle(article);
        } else if ("edit".equals(oper)) {
            articleService.updatearticle(article);
        } else {
            articleService.deleteArticle(article);
        }
    }

    @RequestMapping("/findAllArticle")
    public ResponseEntity<Map<String, Object>> findAllArticle(Integer page, Integer rows) {
        return ResponseEntity.ok(articleService.findAllArticle(page, rows));
    }

    @RequestMapping("/brower")
    public Map<String, Object> brower(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");
        String realPath = request.getSession().getServletContext().getRealPath("img/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        String image = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/img/";
        map.put("current_url", image);
        map.put("total_count", file.length());
        List<Photo> photoList = new ArrayList<Photo>();
        for (File f : files) {
            Photo photo = new Photo();
            boolean directory = f.isDirectory();
            photo.setIs_dir(directory);
            if (directory) {
                int length = f.listFiles().length;
                if (length == 0) {
                    photo.setHas_file(false);
                } else {
                    photo.setHas_file(true);
                }
            } else {
                photo.setHas_file(false);
            }
            photo.setFilesize(f.length());
            photo.setDir_path("");
            String name = f.getName();
            // 文件后缀
            String extension = FilenameUtils.getExtension(name);
            photo.setFiletype(extension);
            photo.setFilename(name);
            if (extension.equals("jpg") || extension.equals("png")) {
                photo.setIs_photo(true);
            } else {
                photo.setIs_photo(false);
            }
            long l = f.lastModified();
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(l));
            photo.setDatetime(format);
            photoList.add(photo);

        }
        map.put("file_list", photoList);
        return map;
    }

    @RequestMapping("/upload")
    public Map<String, Object> upload(MultipartFile imgFile, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(16);
        String realPath = request.getSession().getServletContext().getRealPath("img");
        try {
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            imgFile.transferTo(new File(file.getPath(), imgFile.getOriginalFilename()));
            String path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/img/" + imgFile.getOriginalFilename();
            map.put("error", 0);
            map.put("url", path);
        } catch (IOException e) {
            map.put("error", 1);
            map.put("message", e.getMessage());
        }
        return map;
    }
}
