package com.mp.cmfz;

import com.mp.cmfz.entity.Slide;
import com.mp.cmfz.service.SlideService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzApplication.class)
public class CmfzApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SlideService slideService;

    @Test
    public void Test1() {
        Map<String, Object> map = slideService.findAllSlide(1, 3);
        for (String s : map.keySet()) {
            Object o = map.get(s);
            System.out.println("o = " + o);
        }
    }

    @Test
    public void Test2() {
        Slide slide = new Slide();
        slide.setId("1");
        slideService.deleteSlide(slide);
    }
}
