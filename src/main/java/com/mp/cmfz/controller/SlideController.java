package com.mp.cmfz.controller;

import com.mp.cmfz.entity.Slide;
import com.mp.cmfz.lock.ZookeeperDistributeLock;
import com.mp.cmfz.service.SlideService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/slide")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    private Redisson redisson;

    @RequestMapping("/findAllSlide")
    public Map<String, Object> slideAllSlide(Integer page, Integer rows) {
        Map<String, Object> map = null;
        try {
            map = slideService.findAllSlide(page, rows);
            map.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "查询失败");
        }
        return map;
    }

    @RequestMapping("/exportExcel")
    public ResponseEntity exportExcel() {
        try {
            slideService.exportExcel();
            return ResponseEntity.ok("导出成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping("/importExcel")
    public ResponseEntity importExcel() {
        try {
            Workbook workbook = new HSSFWorkbook(new FileInputStream("D:\\实训/副本slide.xls"));
            Sheet sheet = workbook.getSheet("轮播图");
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i < lastRowNum; i++) {
                Row row = sheet.getRow(i + 2);
                String id = row.getCell(0).getStringCellValue();
                String title = row.getCell(1).getStringCellValue();
                String state = row.getCell(3).getStringCellValue();
                Date createDate = row.getCell(4).getDateCellValue();
                Date last_update_date = row.getCell(5).getDateCellValue();
                Slide slide = new Slide();
                slide.setId(id).setTitle(title).setStatus(Integer.parseInt(state))
                        .setCreate_date(createDate)
                        .setLast_update_date(last_update_date);
                System.out.println("slide = " + slide);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @RequestMapping("/addSlide")
    public Map<String, Object> addSlide(Slide slide, String oper) {
        Map<String, Object> map = new HashMap<>();

        if ("add".equals(oper)) {
            try {
                String id = add(slide);
                map.put("code", 200);
                map.put("data", id);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 500);
                map.put("msg", e.getMessage());
            }
        } else if ("del".equals(oper)) {

            slideService.deleteSlide(slide);
        } else {
            slideService.updateSlide(slide);
        }
        return map;
    }

    public String add(Slide slide) {
        String id = slideService.addSlide(slide);
        return id;
    }

    public String update(Slide slide) {
        String id = slideService.updateSlide(slide);
        return id;
    }

    @RequestMapping("/upload")
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {

        slideService.upload(id, cover, request);
    }


    @RequestMapping("/stock")
    public String stock() {
        ZookeeperDistributeLock zookeeperDistributeLock =
                new ZookeeperDistributeLock("/stock");
        try {
            zookeeperDistributeLock.lock();
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock != 0) {
                stock = stock - 1;
                System.out.println("库存-1,操作完成,剩余库存为" + stock);
                stringRedisTemplate.opsForValue().set("stock", stock + "");
            } else {
                System.out.println("库存不足");
            }
            return "end";
        } finally {
            zookeeperDistributeLock.unlock();
        }
    }


}
