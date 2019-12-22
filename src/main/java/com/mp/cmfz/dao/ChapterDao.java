package com.mp.cmfz.dao;

import com.mp.cmfz.entity.Chapter;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChapterDao extends Mapper<Chapter> {
}
