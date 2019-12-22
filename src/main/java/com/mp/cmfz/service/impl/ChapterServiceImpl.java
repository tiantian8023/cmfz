package com.mp.cmfz.service.impl;

import com.mp.cmfz.dao.ChapterDao;
import com.mp.cmfz.entity.Chapter;
import com.mp.cmfz.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Override
    public Map<String, Object> findAllChapter(Integer page, Integer rows, String albumId) {
        Example example = new Example(Chapter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("albumId", albumId);
        int count = chapterDao.selectCountByExample(example);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chapter> chapters = chapterDao.selectByExampleAndRowBounds(example, rowBounds);
        Map map = new HashMap();
        map.put("records", count);
        map.put("rows", chapters);
        map.put("page", page);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        return map;
    }

    @Override
    public String updateChapter(Chapter chapter) {
        String id = chapter.getId();
        int count = chapterDao.updateByPrimaryKeySelective(chapter);
        return id;
    }

    @Override
    public void deleteChapter(Chapter chapter) {
        chapterDao.deleteByPrimaryKey(chapter);
    }

    @Override
    public String addChapter(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString())
                .setCreateDate(new Date())
                .setLastUpdateDate(new Date());
        String id = chapter.getId();
        chapterDao.insert(chapter);
        return id;
    }

    @Override
    public void upload(MultipartFile audioPath, String id, HttpServletRequest request) throws IOException {
        Date date = null;
        String StringDate = null;
        String realPath = request.getSession().getServletContext().getRealPath("/");
        File file = new File(realPath + "mp3");
        if (!file.exists()) {
            file.mkdir();
        }
        String filename = audioPath.getOriginalFilename();
        // 计算文件大小
        audioPath.transferTo(new File(file.getPath(), filename));
        long size = audioPath.getSize();
        BigDecimal bigDecimal = BigDecimal.valueOf(audioPath.getSize());
        int BigDecimalSize = 1024 * 1024;
        BigDecimal bigDecima2 = BigDecimal.valueOf(BigDecimalSize);
        BigDecimal divide = bigDecimal.divide(bigDecima2, 2, RoundingMode.HALF_UP);
        try {
            // 计算文件时长
            File newFIle = new File(file + "/" + filename);
            Encoder encoder = new Encoder();
            MultimediaInfo info = encoder.getInfo(newFIle);
            long duration = info.getDuration() - 8 * 60 * 60 * 1000;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            date = new Date(duration);
            StringDate = simpleDateFormat.format(date);
            System.out.println("StringDate = " + StringDate);
//            long s =  (duration / 1000);
//            System.out.println("s = " + s);
//            String hour = String.valueOf((s/3600));
//            String minute = String.valueOf((s%3600)/60);
//            String second = String.valueOf((s%3600)%60);
//            StringDate = hour+":"+minute+":"+second;
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//            date = simpleDateFormat.parse(StringDate);
//            System.out.println("s = " + s);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        Chapter chapter = new Chapter();
        chapter.setId(id)
                .setSize(divide.doubleValue() + "MB")
                .setAudioPath(filename)
                .setDuration(StringDate);
        chapterDao.updateByPrimaryKeySelective(chapter);
    }


}
