package com.mp.cmfz.controller;

import com.mp.cmfz.entity.Album;
import com.mp.cmfz.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping("/findAllAlbum")
    public ResponseEntity<Map<String, Object>> findAllAlbum(Integer page, Integer rows) {
        return ResponseEntity.ok(albumService.findAllAlbum(page, rows));
    }

    @RequestMapping("/addAlbum")
    public Map<String, Object> addAlbum(Album album, String oper) {
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            String id = albumService.addAlbum(album);
            map.put("code", 200);
            map.put("id", id);
        } else if ("del".equals(oper)) {
            albumService.deleteAlbum(album);
        } else {

        }
        return map;
    }

    @RequestMapping("/upload")
    public ResponseEntity<Void> upload(String id, MultipartFile cover, HttpServletRequest request) {
        albumService.upload(id, cover, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping("/exportExcel")
    public ResponseEntity exportExcel() {
        try {
            albumService.exportExcel();
            return ResponseEntity.ok("导出专辑成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
