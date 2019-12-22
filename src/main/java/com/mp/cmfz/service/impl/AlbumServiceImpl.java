package com.mp.cmfz.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.mp.cmfz.dao.AlbumDao;
import com.mp.cmfz.dao.ChapterDao;
import com.mp.cmfz.entity.Album;
import com.mp.cmfz.entity.Chapter;
import com.mp.cmfz.enums.ExceptionEnum;
import com.mp.cmfz.exception.MyException;
import com.mp.cmfz.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private ChapterDao chapterDao;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String addAlbum(Album album) {
        String id = UUID.randomUUID().toString();
        album.setId(id).
                setCreateDate(new Date()).
                setLastUpdateDate(new Date()).
                setPublishDate(new Date());
        int count = albumDao.insert(album);
        if (count != 1) {
            throw new MyException(ExceptionEnum.ALBUM_SAVE_ERROR);
        }
        return id;
    }

    @Override
    public Map<String, Object> findAllAlbum(Integer page, Integer rows) {
        Example example = new Example(Album.class);
        int count = albumDao.selectCountByExample(example);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Album> albums = albumDao.selectByExampleAndRowBounds(example, rowBounds);
        if (CollectionUtils.isEmpty(albums)) {
            throw new MyException(ExceptionEnum.ALBUM_NOT_FOUND);
        }
        Map map = new HashMap();
        map.put("records", count);
        map.put("rows", albums);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("page", page);
        return map;
    }

    @Override
    public void upload(String id, MultipartFile cover, HttpServletRequest request) {

        String filename = cover.getOriginalFilename();
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/");
            File file = new File(realPath + "img");
            if (!file.exists()) {
                file.mkdir();
            }
            System.out.println("filename = " + filename);
            long size = cover.getSize();
            //文件类型
            String contentType = cover.getContentType();
            cover.transferTo(new File(file.getPath(), filename));
        } catch (Exception e) {
            throw new MyException(ExceptionEnum.IMAGE_UPLOAD_ERROR);
        }
        Album album = new Album();
        album.setCover(filename).setId(id);
        albumDao.updateByPrimaryKeySelective(album);

    }

    @Override
    public void deleteAlbum(Album album) {
        int count = albumDao.deleteByPrimaryKey(album.getId());
        if (count == 0) {
            throw new MyException(ExceptionEnum.ALBUM_DELETE_ERROR);
        }
    }

    @Override
    public void exportExcel() {
        List<Album> albums = albumDao.selectAll();
        Chapter chapter = new Chapter();
        if (CollectionUtils.isEmpty(albums)) {
            throw new MyException(ExceptionEnum.ALBUM_NOT_FOUND);
        }
        for (Album album : albums) {
            chapter.setAlbumId(album.getId());
            List<Chapter> characters = chapterDao.select(chapter);
            System.out.println(characters);
            album.setChapters(characters);
        }
        System.out.println("albums = " + albums);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams
                ("专辑详情", "album"), Album.class, albums);
        try {
            workbook.write(new FileOutputStream("D:\\实训/album.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
