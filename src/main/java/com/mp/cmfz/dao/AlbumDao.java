package com.mp.cmfz.dao;

import com.mp.cmfz.entity.Album;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AlbumDao extends Mapper<Album> {
}
