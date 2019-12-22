package com.mp.cmfz.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.mp.cmfz.dao.SlideDao;
import com.mp.cmfz.entity.Slide;
import com.mp.cmfz.myannotation.DeleteCatch;
import com.mp.cmfz.myannotation.findCatch;
import com.mp.cmfz.service.SlideService;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideDao slideDao;

    @Override
    @findCatch
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllSlide(Integer page, Integer rows) {
        Example example = new Example(Slide.class);
        int count = slideDao.selectCountByExample(example);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Slide> slides = slideDao.selectByExampleAndRowBounds(example, rowBounds);
        Map map = new HashMap();
        map.put("page", page);
        map.put("rows", slides);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String addSlide(Slide slide) {
        String id = UUID.randomUUID().toString();
        slide.setId(id).setCreate_date(new Date()).setLast_update_date(new Date());
        System.out.println("slide = " + slide);
        int result = slideDao.insertSelective(slide);
        if (result != 1) {
            throw new RuntimeException("添加失败");
        }
        System.out.println(" 添加成功 ");
        return id;
    }

    @DeleteCatch
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void deleteSlide(Slide slide) {
        slideDao.deleteByPrimaryKey(slide.getId());
    }

    @Override
    public String updateSlide(Slide slide) {
        String id = slide.getId();
        int i = slideDao.updateByPrimaryKeySelective(slide);
        return id;
    }

    @Override
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        File file = new File(realPath + "img");
        if (!file.exists()) {
            file.mkdir();
        }
        String filename = cover.getOriginalFilename();
        //获取文件的大小默认是字节
        long size = cover.getSize();
        //文件类型
        String contentType = cover.getContentType();
        cover.transferTo(new File(file.getPath(), filename));

        Slide slide = new Slide();
        slide.setId(id).setCover(filename);
        slideDao.updateByPrimaryKeySelective(slide);
    }

    @Override
    public void exportExcel() {
        List<Slide> list = slideDao.selectAll();
        for (Slide slide : list) {
            slide.setCover("D:\\学习\\idea\\cmfz\\src\\main\\webapp\\img\\timg.jpg");
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图展示", "轮播图"),
                Slide.class, list);
        try {
            workbook.write(new FileOutputStream("D:\\实训/slide.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
