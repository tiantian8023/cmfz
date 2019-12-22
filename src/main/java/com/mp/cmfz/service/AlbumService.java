package com.mp.cmfz.service;

import com.mp.cmfz.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AlbumService {

    String addAlbum(Album album);

    Map<String, Object> findAllAlbum(Integer page, Integer rows);

    void upload(String id, MultipartFile cover, HttpServletRequest request);

    void deleteAlbum(Album album);

    void exportExcel();
}
